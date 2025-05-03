package core.panels.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entities.tank.components.Bullet;
import utils.ImageDrawer;
import utils.Point;

public class GamePanel extends JPanel implements KeyListener {
    public enum Controls {
        UP, DOWN, LEFT, RIGHT, SPACE;

        public static Controls parseControls(KeyEvent e) {
            return switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> Controls.UP;
                case KeyEvent.VK_DOWN -> Controls.DOWN;
                case KeyEvent.VK_LEFT -> Controls.LEFT;
                case KeyEvent.VK_RIGHT -> Controls.RIGHT;
                case KeyEvent.VK_SPACE -> Controls.SPACE;
                default -> null;
            };
        }
    }

    private Point tankPosition;
    private int tankAngle = 0;
    private final int tankSpeed = 10; // TODO: to be removed
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Map<Controls, Boolean> keysPressed = new HashMap<>();
    private Image tankImage;
    private int tankImageWidth;
    private int tankImageHeight;
    private BufferedImage rotatedTankImage;
    private int lastAngle = -1;
    private int ammo = 10;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        tankPosition = new Point(getWidth() / 2, getHeight() / 2);

        loadTankImage("assets/img/tanks/tank_2_2.png");

        Timer gameLoop = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        gameLoop.start();

        setFocusable(true);
        addKeyListener(this);
    }

    private void loadTankImage(String path) {
        ImageDrawer imageDrawer = new ImageDrawer(path);
        tankImage = imageDrawer.getScaledImage(0.25);

        if (tankImage != null) {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(tankImage, 0);
            try {
                tracker.waitForAll();
                tankImageWidth = tankImage.getWidth(null);
                tankImageHeight = tankImage.getHeight(null);
                updateRotatedTankImage();
            } catch (InterruptedException e) {
                System.err.println("Image loading interrupted");
            }
        }
    }

    private void updateRotatedTankImage() {
        if (tankImage == null) return;

        double diagonal = Math.sqrt(tankImageWidth * tankImageWidth + tankImageHeight * tankImageHeight);
        rotatedTankImage = new BufferedImage((int) diagonal, (int) diagonal, BufferedImage.TYPE_INT_ARGB);

        Graphics2D rg2d = rotatedTankImage.createGraphics();

        rg2d.translate(diagonal / 2, diagonal / 2);
        rg2d.rotate(Math.toRadians(-tankAngle));
        rg2d.drawImage(tankImage, -tankImageWidth / 2, -tankImageHeight / 2, null);
        rg2d.dispose();

        lastAngle = -tankAngle;
    }

    private void updateGame() {
        if (keysPressed.getOrDefault(Controls.LEFT, false)) tankAngle = (tankAngle + 5) % 360;
        if (keysPressed.getOrDefault(Controls.RIGHT, false)) tankAngle = (tankAngle - 5 + 360) % 360;

        if (tankAngle != lastAngle) updateRotatedTankImage();

        if (keysPressed.getOrDefault(Controls.UP, false) || keysPressed.getOrDefault(Controls.DOWN, false)) {
            int direction = keysPressed.getOrDefault(Controls.UP, false) ? 1 : -1;
            double angleRad = Math.toRadians(tankAngle);

            int buffer = 5;
            int moveX = (int) (direction * tankSpeed * Math.cos(angleRad));
            int moveY = -(int) (direction * tankSpeed * Math.sin(angleRad));
            int newX = tankPosition.getX() + moveX;
            int newY = tankPosition.getY() + moveY;

            if (newX <= getWidth() - buffer) tankPosition.setX(newX);
            if (newY <= getHeight() - buffer) tankPosition.setY(newY);
        }

        bullets.removeIf(bullet -> !bullet.isFiring());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (rotatedTankImage != null) {
            int drawX = tankPosition.getX() - rotatedTankImage.getWidth() / 2;
            int drawY = tankPosition.getY() - rotatedTankImage.getHeight() / 2;

            g2d.drawImage(rotatedTankImage, drawX, drawY, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(tankPosition.getX(), tankPosition.getY(), 30, 30);
        }

        g2d.setColor(Color.YELLOW);

        for (Bullet bullet : bullets) {
            if (bullet.isFiring()) {
                Point pos = bullet.getCurrentPosition();
                g2d.fillOval(pos.getX() - 3, pos.getY() - 3, 6, 6);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Controls control = Controls.parseControls(e);
        if (control != null) keysPressed.put(control, true);

        if (e.getKeyCode() == KeyEvent.VK_SPACE) fireBullet();
    }

    private void fireBullet() {
        if (tankImage == null || ammo <= 0) return;

        double angleRad = Math.toRadians(tankAngle);

        Point bulletStartPosition = new Point(
                tankPosition.getX() + (int) (tankImageWidth * Math.cos(angleRad) / 2),
                tankPosition.getY() - (int) (tankImageWidth * Math.sin(angleRad) / 2)
        );

        Bullet bullet = new Bullet(bulletStartPosition, tankSpeed + 5, -tankAngle);
        bullets.add(bullet);

        ammo--;
        bullet.fire(() -> {
            ammo++;
            System.out.println("Bullet reached destination");
        });
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Controls control = Controls.parseControls(e);
        if (control != null) keysPressed.put(control, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}