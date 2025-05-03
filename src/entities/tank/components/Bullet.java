package entities.tank.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import utils.Point;

public class Bullet {
    private Timer animationTimer;
    private Point initialPosition;
    private Point currentPosition;
    private int angle;
    private int speed; // pixels per frame
    private boolean isFiring = false;

    private static final int DISTANCE = 200; // total distance to travel

    public Bullet(Point initialPosition, int speed, int angle) {
        this.initialPosition = new Point(initialPosition);
        this.currentPosition = new Point(initialPosition);
        this.speed = speed;
        this.angle = angle;
    }

    public void fire(Runnable onCompletion) {
        if (isFiring) return; // Prevent multiple fires

        isFiring = true;
        final int totalFrames = DISTANCE / speed;
        final double angleRad = Math.toRadians(angle);
        final double dx = speed * Math.cos(angleRad);
        final double dy = speed * Math.sin(angleRad);

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

                // Update position
                currentPosition.setX((int) (initialPosition.getX() + dx * framesPassed));
                currentPosition.setY((int) (initialPosition.getY() + dy * framesPassed));
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