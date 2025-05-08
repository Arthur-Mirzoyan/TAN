package entities.tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.Dimension;
import utils.Entity;
import utils.ImageDrawer;
import utils.Point;
import utils.Map;
import core.panels.Game.GamePanel;

public class Tank extends Entity {
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
    private static final int Map_Bounds_Buffer_Zone = 5;

    private final String id;
    private final TankHull hull;
    private final TankCannon cannon;
    private final Map map;
    private final HashMap<Controls, Boolean> keysPressed = new HashMap<>();

    private Image image;
    private BufferedImage rotatedImage;
    private int angle = 0;
    private int lastAngle = -1;

    public Tank(Point position, TankHull hull, TankCannon cannon, Map map, User owner) {
        super(position);

        this.hull = hull;
        this.cannon = cannon;
        this.cannon.setOwner(owner);
        this.map = map;
        this.id = "tank_" + hull.getId() + "_" + cannon.getId();

        cannon.setBulletSpeed(cannon.getBulletSpeed() + hull.getSpeed());

        loadImage();

        setDimension(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    // Ghost tank copy constructor
    // Not a full copy
    private Tank(Tank other, int newX, int newY, int angle) {
        super(other);

        this.hull = null;
        this.cannon = null;
        this.id = null;
        this.map = null;

        setPosition(new Point(newX, newY));
        setAngle(angle);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        setRotation(angle);

        if (lastAngle != angle) updateRotatedTankImage();
    }

    public int getSpeed() {
        return hull.getSpeed();
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

    public void onKeyReleased(KeyEvent e, ArrayList<User> users) {
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
            int direction = keysPressed.getOrDefault(Controls.UP, false) ? 1 : -1;
            int speed = hull.getSpeed();
            double angleRad = Math.toRadians(angle);

            newX += (int) (direction * speed * Math.cos(angleRad));
            newY -= (int) (direction * speed * Math.sin(angleRad));
        }

        if ((new Tank(this, newX, newY, newAngle)).isFreeOfCollisions(map)) {
            setPosition(new Point(newX, newY));
            setAngle(newAngle);
        }

//        cannon.verifyFiringBulletsList(); // might be redundant
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

    private void shoot(ArrayList<User> users) {
        Point[] corners = getCorners();
        Point topRightCorner = corners[1];
        Point bottomRightCorner = corners[2];

        cannon.shoot(map,
                users,
                new Point((topRightCorner.getX() + bottomRightCorner.getX()) / 2, (topRightCorner.getY() + bottomRightCorner.getY()) / 2),
                angle);
    }
}
