package entities.tank;

import entities.mysteryBox.MysteryBox;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.components.UserData;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point;
import utils.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a tank in the game, combining a hull and cannon with player controls.
 * The tank handles rendering, movement, shooting, and interactions with map elements.
 */
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

    private Runnable onTankMove;
    private Image image;
    private BufferedImage rotatedImage;
    private Map map;
    private int angle = 0;
    private int lastAngle = -1;

    /**
     * Constructs a tank with specific components, map, and owner.
     */
    public Tank(Point position, TankHull hull, TankCannon cannon, Map map, UserData owner) {
        super(position);

        this.hull = hull;
        this.cannon = cannon;
        this.cannon.setOwner(owner);
        this.map = map;
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();

        this.prepareTank();
    }

    /**
     * Copy constructor to create a new tank instance from another.
     */
    public Tank(Tank tank) {
        super(tank.getPosition());

        this.hull = tank.getHull();
        this.cannon = tank.getCannon();
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();
        setPosition(tank.getPosition());
        setAngle(angle);

        this.prepareTank();
    }

    /**
     * Used when only the components are provided, not the full state.
     */
    public Tank(TankHull hull, TankCannon cannon) {
        super(new Point());

        this.hull = hull;
        this.cannon = cannon;
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();

        this.prepareTank();
    }

    /**
     * Constructs a tank from a JSON object.
     */
    public Tank(JSONObject json) {
        super(new Point());

        this.id = JSONHelper.getValue(json, "id", "");
        this.hull = new TankHull(JSONHelper.getValue(json, "hull", new JSONObject()));
        this.cannon = new TankCannon(JSONHelper.getValue(json, "cannon", new JSONObject()));

        try {
            this.position = new Point((JSONObject) json.get("position"));
        } catch (JSONException e) {
        }

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

    public void setOnTankMove(Runnable action) {
        this.onTankMove = action;
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
        return hull;
    }

    public TankCannon getCannon() {
        return cannon;
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

    /**
     * Updates the tank's position and angle based on input keys.
     */
    public void updateTankPosition() {
        int newAngle = angle;
        int newX = position.getX();
        int newY = position.getY();

        if (keysPressed.getOrDefault(Controls.LEFT, false)) newAngle = (angle + 5) % 360;
        if (keysPressed.getOrDefault(Controls.RIGHT, false)) newAngle = (angle - 5) % 360;

        if (keysPressed.getOrDefault(Controls.UP, false) || keysPressed.getOrDefault(Controls.DOWN, false)) {
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
                        box.action(this);
                        mysteryBoxes.remove(box);
                    }
                }
            }

            onTankMove.run();
        }
    }

    /**
     * Returns the tank configuration in JSON format.
     */
    public JSONObject toJSON(boolean withHealth, boolean withPosition) {
        JSONObject json = new JSONObject();

        json.put("id", id);
        json.put("hull", hull.toJSON(withHealth));
        json.put("cannon", cannon.toJSON());

        if (withPosition) json.put("position", position.toJSON());

        return json;
    }

    public JSONObject toJSON(boolean withHealth) {
        return toJSON(withHealth, false);
    }

    public JSONObject toJSON() {
        return toJSON(false, false);
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

    /**
     * Triggers the tank to fire a bullet, if possible.
     */
    private void shoot(CopyOnWriteArrayList<UserData> users) {
        Point[] corners = getCorners();
        Point topRightCorner = corners[1];
        Point bottomRightCorner = corners[2];

        cannon.shoot(map,
                users,
                new Point((topRightCorner.getX() + bottomRightCorner.getX()) / 2, (topRightCorner.getY() + bottomRightCorner.getY()) / 2),
                angle,
                user -> user.setScore(user.getScore() + 10));
    }

    /**
     * Applies damage to the tank, factoring in its armor.
     */
    public void hit(int damage) {
        hull.setHealth((int) (hull.getHealth() - damage * (1 - hull.getArmorStrength())));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        return id.equals(((Tank) obj).id);
    }

    @Override
    public String toString() {
        return "Tank{" +
                "id='" + id + '\'' +
                ", position=" + position +
                '}';
    }
}
