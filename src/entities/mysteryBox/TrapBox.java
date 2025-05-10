package entities.mysteryBox;

import entities.tank.Tank;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import utils.Point;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code TrapBox} class is a type of {@link MysteryBox} that applies
 * negative effects to a tank, such as reducing stats or applying delays.
 */
public class TrapBox extends MysteryBox {

    /**
     * Constructs a {@code TrapBox} at the specified location with a given size.
     *
     * @param point     the location of the box
     * @param dimension the dimensions of the box
     */
    public TrapBox(Point point, Dimension dimension) {
        super(point, dimension);
    }

    private void decreaseSpeedAffect(TankHull hull) {
        int speed = hull.getSpeed();
        hull.setSpeed(0);

        Timer timer = new Timer(10000, e -> hull.setSpeed(speed));
        timer.setRepeats(false);
        timer.start();
    }

    private void decreaseAmmoAffect(TankCannon cannon) {
        cannon.setAmmo(cannon.getAmmo() - 10);
    }

    private void subtractHealthAffect(TankHull hull, int health) {
        hull.setHealth(hull.getHealth() + health);
    }

    private void decreaseBulletSpeedAffect(TankCannon cannon) {
        cannon.setBulletSpeed((int) (cannon.getBulletSpeed() * 0.8));

        Timer timer = new Timer(10000, e -> cannon.setBulletSpeed((int) (cannon.getBulletSpeed() / 0.8)));
        timer.setRepeats(false);
        timer.start();
    }

    private void reloadBulletSpeedAffect(TankCannon cannon) {
        cannon.setBulletSpeed((int) (cannon.getBulletSpeed() * 0.8));

        Timer timer = new Timer(10000, e -> cannon.setBulletSpeed((int) (cannon.getBulletSpeed() / 0.8)));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Triggers a random negative effect on the provided tank.
     *
     * @param tank the tank to be negatively affected
     */
    @Override
    public void action(Tank tank) {
        Runnable[] effects = new Runnable[]{
                () -> decreaseSpeedAffect(tank.getHull()),
                () -> decreaseAmmoAffect(tank.getCannon()),
                () -> subtractHealthAffect(tank.getHull(), (int) (Math.random() * 30 + 10)),
                () -> decreaseBulletSpeedAffect(tank.getCannon()),
                () -> reloadBulletSpeedAffect(tank.getCannon())
        };

        effects[(int) (Math.random() * effects.length)].run();
    }
}
