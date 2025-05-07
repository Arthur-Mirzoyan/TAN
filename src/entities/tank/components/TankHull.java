package entities.tank.components;

import org.json.JSONObject;
import utils.JSONHelper;

import javax.swing.*;

public class TankHull implements Cloneable {
    private final int id;
    private final String name;
    private final int price;

    private int armorStrength;
    private int speed;

    private int health;
    private Level level;

    public TankHull(int id, String name, int price, int speed, int armorStrength) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.armorStrength = armorStrength;
        this.health = 100;
        this.level = Level.PRIVATE;
    }

    public TankHull(JSONObject json) {
        this.id = JSONHelper.getValue(json, "id", 1);
        this.name = JSONHelper.getValue(json, "name", "");
        this.price = JSONHelper.getValue(json, "price", 1);
        this.speed = JSONHelper.getValue(json, "speed", 1);
        this.armorStrength = JSONHelper.getValue(json, "armorStrength", 1);
        this.level = Level.PRIVATE; // TODO: should take from json
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

    public int getArmorStrength() {
        return armorStrength;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public Level getLevel() {
        return level;
    }

    public void setArmorStrength(int armorStrength) {
        this.armorStrength = armorStrength;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void upgrade() {
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("id", id);
        json.put("name", name);
        json.put("price", price);
        json.put("speed", speed);
        json.put("armorStrength", armorStrength);

        return json;
    }

    @Override
    public TankHull clone() throws CloneNotSupportedException {
        return (TankHull) super.clone();
    }
}
