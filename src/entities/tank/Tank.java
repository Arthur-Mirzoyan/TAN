package entities.tank;

import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;

public class Tank {
    private final String id;
    private final String name;
    private final int price;

    private TankHull hull;
    private TankCannon cannon;

    public Tank(String id, String name, int price, TankHull hull, TankCannon cannon) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.hull = hull;
        this.cannon = cannon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public TankHull getHull() {
        return hull;
    }

    public TankCannon getCannon() {
        return cannon;
    }

    public void setHull(TankHull hull) {
        this.hull = hull;
    }

    public void setCannon(TankCannon cannon) {
        this.cannon = cannon;
    }
}
