package entities.tank.components;

import java.util.ArrayList;

import org.json.JSONObject;

import utils.Point;
import utils.Map;

public class TankCannon {
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
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.price = json.getInt("price");
        this.bulletSpeed = json.getInt("bulletSpeed");
        this.reloadSpeed = json.getInt("reloadSpeed");
        this.ammo = json.getInt("ammo");
        this.blankShootRate = json.getInt("blankShootRate");
        this.firingRange = json.getInt("firingRange");
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

    public void draw() {
    }

    public void upgrade() {
    }
}
