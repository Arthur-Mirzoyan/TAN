package entities.tank.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import utils.*;
import utils.Dimension;
import utils.Point;

public class Bullet extends Collider {
    public static final int SIZE = 6;
    public static final Color COLOR = Values.ACCENT_COLOR;

    private Timer animationTimer;
    private Point initialPosition;
    private Point currentPosition;
    private int angle;
    private int speed; // pixels per frame
    private int range;
    private boolean isFiring = false;

    public Bullet(Point initialPosition, int speed, int angle, int range) {
        super(initialPosition, new Dimension(SIZE, SIZE));

        setRotation(angle);

        this.initialPosition = new Point(initialPosition);
        this.currentPosition = new Point(initialPosition);
        this.speed = speed;
        this.angle = angle;
        this.range = range;
    }

    public void fire(Map map, Runnable onCompletion) {
        if (isFiring) return;

        isFiring = true;
        final int totalFrames = range / speed;
        final double angleRad = Math.toRadians(angle);
        final double dx = speed * Math.cos(angleRad);
        final double dy = speed * Math.sin(angleRad);

        final byte[][] layout = map.getLayout();
        final int cellSize = map.getCellSize();


        animationTimer = new Timer(16, new ActionListener() { // ~60fps
            private int framesPassed = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (framesPassed >= totalFrames) {
                    animationTimer.stop();
                    isFiring = false;

                    if (onCompletion != null) onCompletion.run();

                    return;
                }

                currentPosition.setX((int) (initialPosition.getX() + dx * framesPassed));
                currentPosition.setY((int) (initialPosition.getY() + dy * framesPassed));

                int row = currentPosition.getY() / cellSize;
                int col = currentPosition.getX() / cellSize;

                // Hitting wall eliminates the bullet
                if (layout[row][col] == Map.WALL) framesPassed = totalFrames;

                // TODO: implement tank penetration logic

                framesPassed++;

                // Here you would typically trigger a repaint of your game component
                // For example: gamePanel.repaint();
            }
        });

        animationTimer.start();
    }

    // Get current position for rendering
    public Point getCurrentPosition() {
        return currentPosition;
    }

    // Check if bullet is currently firing
    public boolean isFiring() {
        return isFiring;
    }

    // Stop the bullet animation
    public void stop() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            isFiring = false;
        }
    }
}