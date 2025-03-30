package entities.user.components;

import java.util.ArrayList;

import entities.tank.Tank;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;

public class Inventory {
    private int money;
    private TankCannon[] cannons;
    private TankHull[] hulls;
    private ArrayList<Tank> tanks;

    public Inventory() {
        money = 0;
        cannons = new TankCannon[5];
        hulls = new TankHull[5];
        tanks = new ArrayList<>();
    }

    public int getMoney() {
        return money;
    }

    public TankCannon[] getCannons() {
        return cannons;
    }

    public TankHull[] getHulls() {
        return hulls;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
    }

    public void addCannon(TankCannon cannon) {
        for (int i = 0; i < cannons.length; i++) {
            if (cannons[i] == null) {
                cannons[i] = cannon;
                return;
            }
        }
    }

    public void addHull(TankHull hull) {
        for (int i = 0; i < hulls.length; i++) {
            if (hulls[i] == null) {
                hulls[i] = hull;
                return;
            }
        }
    }
}
