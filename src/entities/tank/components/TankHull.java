package entities.tank.components;

import org.json.JSONObject;
import utils.JSONHelper;

/**
 * Represents the hull (base) of a tank, defining armor, speed, and health.
 */
public class TankHull implements Cloneable {
    private final int id;
    private final String name;
    private final int price;

    private int speed;
    private double armorStrength;
    private int health;

    public TankHull(int id, String name, int price, int speed, double armorStrength) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.armorStrength = armorStrength;
        this.health = 100;
    }

    public TankHull(JSONObject json) {
        this.id = JSONHelper.getValue(json, "id", 1);
        this.name = JSONHelper.getValue(json, "name", "");
        this.price = JSONHelper.getValue(json, "price", 1);
        this.speed = JSONHelper.getValue(json, "speed", 1);
        this.armorStrength = JSONHelper.getValue(json, "armorStrength", 20) / 100.0;
        this.health = 100;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public double getArmorStrength() {
        return armorStrength;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setArmorStrength(int armorStrength) {
        this.armorStrength = armorStrength;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns the hull data in JSON format.
     */
    public JSONObject toJSON(boolean withHealth) {
        JSONObject json = new JSONObject();

        json.put("id", id);
        json.put("name", name);
        json.put("price", price);
        json.put("speed", speed);
        json.put("armorStrength", (int) (armorStrength * 100));

        if (withHealth) json.put("health", health);

        return json;
    }

    public JSONObject toJSON() {
        return toJSON(false);
    }

    @Override
    public TankHull clone() throws CloneNotSupportedException {
        return (TankHull) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        return id == ((TankHull) obj).id;
    }

    @Override
    public String toString() {
        return "TankHull{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", speed=" + speed +
                ", armorStrength=" + armorStrength +
                ", health=" + health +
                '}';
    }
}
