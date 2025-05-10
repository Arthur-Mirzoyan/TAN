package entities.tank.components;

import entities.tank.Tank;
import entities.user.components.UserData;
import org.json.JSONObject;
import utils.Point;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Bullet extends Collider {
    public static final int SIZE = 6;
    public static final Color COLOR = Values.ACCENT_COLOR;

    private final UserData owner;

    private Timer animationTimer;
    private Point initialPosition;
    private Point currentPosition;
    private int angle;
    private int speed; // pixels per frame
    private int range;
    private boolean isFiring;

    public Bullet(UserData owner, Point initialPosition, int speed, int angle, int range) {
        super(initialPosition, new Dimension(SIZE, SIZE));

        setRotation(angle);

        this.initialPosition = new Point(initialPosition);
        this.currentPosition = new Point(initialPosition);
        this.speed = speed;
        this.angle = angle;
        this.range = range;
        this.owner = owner;
    }

    public Bullet(JSONObject json) {
        super(new Point(JSONHelper.getValue(json, "initialPosition", new Point())), new Dimension(SIZE, SIZE));
        this.initialPosition = this.getPosition();
        this.currentPosition = new Point(JSONHelper.getValue(json, "currentPosition", new Point()));
        this.speed = JSONHelper.getValue(json, "speed", 0);
        this.angle = JSONHelper.getValue(json, "angle", 0);
        this.range = JSONHelper.getValue(json, "range", 0);
        this.isFiring = JSONHelper.getValue(json, "isFiring", false);
        this.owner = JSONHelper.getValue(json, "owner", null);
    }

    public void fire(Map map, CopyOnWriteArrayList<UserData> users, Runnable onCompletion, Consumer<UserData> onTankPenetration) {
        if (isFiring) return;

        isFiring = true;
        final int totalFrames = range / speed;
        final double angleRad = Math.toRadians(angle);
        final double dx = speed * Math.cos(angleRad);
        final double dy = speed * Math.sin(angleRad);

        final byte[][] layout = map.getLayout();
        final int cellSize = map.getCellSize();


        animationTimer = new Timer(16, new ActionListener() { // ~60fps
            int framesPassed = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (framesPassed >= totalFrames) {
                    stop();

                    if (onCompletion != null) onCompletion.run();

                    return;
                }

                currentPosition.setX((int) (initialPosition.getX() + dx * framesPassed));
                currentPosition.setY((int) (initialPosition.getY() + dy * framesPassed));

                int row = currentPosition.getY() / cellSize;
                int col = currentPosition.getX() / cellSize;

                setPosition(currentPosition);

                if (layout[row][col] == Map.WALL) stop();

                int damage = owner.getTank().getCannon().getDamage();

                for (UserData user : users) {
                    Tank target = user.getTank();

                    if (user != owner && collidesWith(target)) {
                        target.hit(damage);
                        onTankPenetration.accept(user);
                        stop();
                        return;
                    }
                }

                framesPassed++;
            }
        });

        animationTimer.start();
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("initialPosition", initialPosition);
        json.put("currentPosition", currentPosition);
        json.put("speed", speed);
        json.put("angle", angle);
        json.put("isFiring", isFiring);
        json.put("range", range);

        return json;
    }

    public boolean isFiring() {
        return isFiring;
    }

    public void stop() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            isFiring = false;
        }
    }
}