package entities.mysteryBox;

import javax.swing.*;

import utils.Point;
import entities.tank.Tank;
import entities.tank.components.TankHull;
import entities.tank.components.TankCannon;

public class BonusBox extends MysteryBox {
    public static final int BONUS_INDEX = 2;

    public BonusBox(Point point) {
        super(point);
    }

    private void increaseSpeedAffect(TankHull hull) {
        hull.setSpeed((int) (hull.getSpeed() * 1.2));
    }

    private void increaseArmourAffect(TankHull hull) {
        hull.setArmorStrength((int) (hull.getArmorStrength() * 1.2));
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
                () -> increaseArmourAffect(tank.getHull()),
                () -> resetHealthAffect(tank.getHull()),
                () -> addHealthAffect(tank.getHull(), (int) (Math.random() * 40 + 10)),
                () -> increaseBulletSpeedAffect(tank.getCannon()),
                () -> reloadBulletSpeedAffect(tank.getCannon())
        };

        effects[(int) (Math.random() * effects.length)].run();
    }
}