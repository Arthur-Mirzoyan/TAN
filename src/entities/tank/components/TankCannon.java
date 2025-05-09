package entities.tank.components;

import java.awt.*;
import java.awt.Dimension;
import java.util.ArrayList;

import entities.tank.Tank;
import org.json.JSONObject;

import utils.*;
import utils.Point;

import javax.swing.*;

public class TankCannon implements Cloneable {
    private final int id;
    private final String name;
    private final int price;

    private int blankShootRate;
    private int bulletSpeed;
    private int reloadSpeed;
    private int ammo;
    private int firingRange;
    private Level level;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public TankCannon(int id, String name, int price, int bulletSpeed, int reloadSpeed, int ammo, int blankShootRate, int firingRange) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bulletSpeed = bulletSpeed;
        this.reloadSpeed = reloadSpeed;
        this.ammo = ammo;
        this.blankShootRate = blankShootRate;
        this.firingRange = firingRange;
        this.level = Level.PRIVATE;
    }

    public TankCannon(JSONObject json) {
        this.id = JSONHelper.getValue(json, "id", 1);
        this.name = JSONHelper.getValue(json, "name", "");
        this.price = JSONHelper.getValue(json, "price", 0);
        this.bulletSpeed = JSONHelper.getValue(json, "bulletSpeed", 1);
        this.reloadSpeed = JSONHelper.getValue(json, "reloadSpeed", 1);
        this.ammo = JSONHelper.getValue(json, "ammo", 1);
        this.blankShootRate = JSONHelper.getValue(json, "blankShootRate", 1);
        this.firingRange = JSONHelper.getValue(json, "firingRange", 1);
        this.level = Level.PRIVATE; // TODO: should take from json
    }

    public void setReloadSpeed(int reloadSpeed) {
        this.reloadSpeed = reloadSpeed;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void setBlankShootRate(byte blankShootRate) {
        this.blankShootRate = blankShootRate;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getBlankShootRate() {
        return blankShootRate;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public int getReloadSpeed() {
        return reloadSpeed;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getFiringRange() {
        return firingRange;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Level getLevel() {
        return level;
    }

    public void shoot(Map map, Point bulletInitialPosition, int angle) {
        if (ammo <= 0) return;

        Bullet bullet = new Bullet(bulletInitialPosition, bulletSpeed, -angle, firingRange);
        bullets.add(bullet);

        ammo--;
        bullet.fire(map, () -> bullets.remove(bullet));
    }

    public void verifyFiringBulletsList() {
        bullets.removeIf(bullet -> !bullet.isFiring());
    }

    public void upgrade() {
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("price", price);
        json.put("bulletSpeed", bulletSpeed);
        json.put("reloadSpeed", reloadSpeed);
        json.put("blankShootRate", blankShootRate);
        json.put("firingRange", firingRange);
        json.put("ammo", ammo);
        json.put("level", level); // TODO: implement Level getting from json

        return json;
    }

    @Override
    public TankCannon clone() throws CloneNotSupportedException {
        return (TankCannon) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;

        return id == ((TankCannon) obj).id;
    }
}
