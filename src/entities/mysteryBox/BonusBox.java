package entities.mysteryBox;

import entities.tank.Tank;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import utils.Point;

import javax.swing.*;
import java.awt.*;

public class BonusBox extends MysteryBox {
    public BonusBox(Point point, Dimension dimension) {
        super(point, dimension);
    }

    private void increaseSpeedAffect(TankHull hull) {
        hull.setSpeed((int) (hull.getSpeed() * 1.2));
    }

    private void addAmmoAffect(TankCannon cannon) {
        cannon.setAmmo(cannon.getAmmo() + 10);
    }

    private void resetHealthAffect(TankHull hull) {
        hull.setHealth(100);
    }

    private void addHealthAffect(TankHull hull, int health) {
        hull.setHealth(hull.getHealth() + health);
    }

    private void increaseBulletSpeedAffect(TankCannon cannon) {
        cannon.setBulletSpeed((int) (cannon.getBulletSpeed() * 1.2));
    }

    private void reloadBulletSpeedAffect(TankCannon cannon) {
        cannon.setBulletSpeed((int) (cannon.getBulletSpeed() * 1.2));

        Timer timer = new Timer(10000, e -> cannon.setBulletSpeed((int) (cannon.getBulletSpeed() / 1.2)));
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void action(Tank tank) {
        Runnable[] effects = new Runnable[]{
                () -> increaseSpeedAffect(tank.getHull()),
                () -> addAmmoAffect(tank.getCannon()),
                () -> resetHealthAffect(tank.getHull()),
                () -> addHealthAffect(tank.getHull(), (int) (Math.random() * 40 + 10)),
                () -> increaseBulletSpeedAffect(tank.getCannon()),
                () -> reloadBulletSpeedAffect(tank.getCannon())
        };

        effects[(int) (Math.random() * effects.length)].run();
    }
}