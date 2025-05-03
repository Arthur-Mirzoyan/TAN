package entities.tank.components;

import utils.Point;

public class TankCannon {
    private final int id;
    private final String name;
    private final int price;

    private int bulletSpeed;
    private int reloadSpeed;
    private int ammo;
    private byte blankShootRate;
    private Level level;

    public TankCannon(int id, String name, int price, int bulletSpeed, int reloadSpeed, int ammo, byte blankShootRate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bulletSpeed = bulletSpeed;
        this.reloadSpeed = reloadSpeed;
        this.ammo = ammo;
        this.blankShootRate = blankShootRate;
        this.level = Level.PRIVATE;
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

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public int getReloadSpeed() {
        return reloadSpeed;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public byte getBlankShootRate() {
        return blankShootRate;
    }

    public Level getLevel() {
        return level;
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

    public void upgrade() {
    }

    public void shoot() {
        if (ammo <= 0) return;

        ammo--;
        Bullet bullet = new Bullet(new Point(10, 10), 10 ,10);
    }

    public void draw() {
    }
}
