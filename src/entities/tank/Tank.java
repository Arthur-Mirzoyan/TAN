package entities.tank;

import entities.mysteryBox.MysteryBox;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.components.UserData;
import org.json.JSONObject;
import utils.Point;
import utils.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tank extends Collider {
    public enum Controls {
        UP, DOWN, LEFT, RIGHT;

        public static Controls parseControls(KeyEvent e) {
            return switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> Controls.UP;
                case KeyEvent.VK_DOWN -> Controls.DOWN;
                case KeyEvent.VK_LEFT -> Controls.LEFT;
                case KeyEvent.VK_RIGHT -> Controls.RIGHT;
                default -> null;
            };
        }
    }

    public static final double IMAGE_SCALE = 0.1;

    private final String id;
    private final TankHull hull;
    private final TankCannon cannon;
    private final HashMap<Controls, Boolean> keysPressed = new HashMap<>();

    private Image image;
    private BufferedImage rotatedImage;
    private Map map;
    private int angle = 0;
    private int lastAngle = -1;

    public Tank(Point position, TankHull hull, TankCannon cannon, Map map, UserData owner) {
        super(position);

        this.hull = hull;
        this.cannon = cannon;
        this.cannon.setOwner(owner);
        this.map = map;
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();

        this.prepareTank();
    }

    public Tank(Tank tank) {
        super(tank.getPosition());

        this.hull = tank.getHull();
        this.cannon = tank.getCannon();
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();
        setPosition(tank.getPosition());
        setAngle(angle);

        this.prepareTank();
    }

    public Tank(TankHull hull, TankCannon cannon) {
        super(new Point());

        this.hull = hull;
        this.cannon = cannon;
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();

        this.prepareTank();
    }

    public Tank(JSONObject json) {
        super(new Point());

        this.id = JSONHelper.getValue(json, "id", "");
        this.hull = new TankHull(JSONHelper.getValue(json, "hull", new JSONObject()));
        this.cannon = new TankCannon(JSONHelper.getValue(json, "cannon", new JSONObject()));

        this.prepareTank();
    }

    // Not a copy constructor (Ghost tank)
    private Tank(Tank other, Point position, int angle) {
        super(other);

        this.hull = null;
        this.cannon = null;
        this.id = null;

        setPosition(position);
        setAngle(angle);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        setRotation(angle);

        if (lastAngle != angle) updateRotatedTankImage();
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setOwner(UserData user) {
        this.cannon.setOwner(user);
    }

    public int getSpeed() {
        return hull.getSpeed();
    }

    public String getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public BufferedImage getRotatedImage() {
        return rotatedImage;
    }

    public TankHull getHull() {
        try {
            return hull.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Tank Hull was not cloned. Returning null.");
            return null;
        }
    }

    public TankCannon getCannon() {
        try {
            return cannon.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Tank Cannon was not cloned. Returning null.");
            return null;
        }
    }

    public void onKeyPressed(KeyEvent e) {
        Controls control = Controls.parseControls(e);
        if (control != null) keysPressed.put(control, true);
    }

    public void onKeyReleased(KeyEvent e, CopyOnWriteArrayList<UserData> users) {
        Controls control = Controls.parseControls(e);
        if (control != null) keysPressed.put(control, false);

        if (e.getKeyCode() == KeyEvent.VK_SPACE) shoot(users);
    }

    public void updateTankPosition() {
        int newAngle = angle;
        int newX = position.getX();
        int newY = position.getY();

        if (keysPressed.getOrDefault(Controls.LEFT, false)) newAngle = (angle + 5) % 360;
        if (keysPressed.getOrDefault(Controls.RIGHT, false)) newAngle = (angle - 5) % 360;

        if (keysPressed.getOrDefault(Controls.UP, false) || keysPressed.getOrDefault(Controls.DOWN, false)) {
            System.out.println("Up or Down");
            int direction = keysPressed.getOrDefault(Controls.UP, false) ? 1 : -1;
            int speed = hull.getSpeed();
            double angleRad = Math.toRadians(angle);

            newX += (int) (direction * speed * Math.cos(angleRad));
            newY -= (int) (direction * speed * Math.sin(angleRad));
        }

        if ((new Tank(this, new Point(newX, newY), newAngle)).isFreeOfCollisions(map)) {
            setPosition(new Point(newX, newY));
            setAngle(newAngle);

            CopyOnWriteArrayList<MysteryBox> mysteryBoxes = map.getMysteryBoxesToDraw();
            if (mysteryBoxes != null) {
                for (MysteryBox box : mysteryBoxes) {
                    if (box.collidesWith(this)) {
                        mysteryBoxes.remove(box);
                        box.action(this);
                    }
                }
            }
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("id", id);
        json.put("hull", hull.toJSON());
        json.put("cannon", cannon.toJSON());

        return json;
    }

    private void prepareTank() {
        cannon.setBulletSpeed(cannon.getBulletSpeed() + hull.getSpeed());
        loadImage();

        if (image != null)
            setDimension(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    private void updateRotatedTankImage() {
        if (image == null) return;

        int tankImageWidth = image.getWidth(null);
        int tankImageHeight = image.getHeight(null);
        double diagonal = Math.sqrt(tankImageWidth * tankImageWidth + tankImageHeight * tankImageHeight);

        rotatedImage = new BufferedImage((int) diagonal, (int) diagonal, BufferedImage.TYPE_INT_ARGB);

        Graphics2D rg2d = rotatedImage.createGraphics();

        rg2d.translate(diagonal / 2, diagonal / 2);
        rg2d.rotate(Math.toRadians(-angle));
        rg2d.drawImage(image, -tankImageWidth / 2, -tankImageHeight / 2, null);
        rg2d.dispose();

        lastAngle = -angle;
    }

    private void loadImage() {
        ImageDrawer imageDrawer = new ImageDrawer("assets/img/tanks/" + id + ".png");
        image = imageDrawer.getScaledImage(IMAGE_SCALE);

        updateRotatedTankImage();
    }

    private void shoot(CopyOnWriteArrayList<UserData> users) {
        Point[] corners = getCorners();
        Point topRightCorner = corners[1];
        Point bottomRightCorner = corners[2];

        cannon.shoot(map,
                users,
                new Point((topRightCorner.getX() + bottomRightCorner.getX()) / 2, (topRightCorner.getY() + bottomRightCorner.getY()) / 2),
                angle,
                user -> {
                    // TODO: Send to server
                });
    }

    public void hit(int damage) {
        hull.setHealth(hull.getHealth() - damage * (1 - hull.getArmorStrength()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        return id.equals(((Tank) obj).id);
    }
}
