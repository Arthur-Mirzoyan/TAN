package entities.tank.components;

import entities.tank.Tank;
import org.json.JSONObject;
import utils.CustomComponents;
import utils.ImageDrawer;
import utils.JSONHelper;
import utils.Values;
import entities.user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class TankHull implements Cloneable {
    private final int id;
    private final String name;
    private final int price;

    private int speed;
    private double armorStrength;
    private double health;
    private Level level;

    public TankHull(int id, String name, int price, int speed, double armorStrength) {
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
        this.armorStrength = JSONHelper.getValue(json, "armorStrength", 20) / 100.0;
        this.level = Level.parseLevel(JSONHelper.getValue(json, "level", ""));
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

    public double getHealth() {
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

    public void setHealth(double health) {
        this.health = Math.max(health, 0);
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
        json.put("armorStrength", (int) (armorStrength * 100));

        return json;
    }

    @Override
    public TankHull clone() throws CloneNotSupportedException {
        return (TankHull) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;

        return id == ((TankHull) obj).id;
    }
}
