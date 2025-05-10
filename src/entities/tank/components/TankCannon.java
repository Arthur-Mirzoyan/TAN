package entities.tank.components;

import entities.user.components.UserData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.JSONHelper;
import utils.Map;
import utils.Point;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Represents a tank cannon with damage, firing range, ammo, and bullet behavior.
 */
public class TankCannon implements Cloneable {
    private final int id;
    private final String name;
    private final int price;

    private UserData owner;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int bulletSpeed;
    private int ammo;
    private int firingRange;
    private int damage;

    public TankCannon(int id, String name, int price, int bulletSpeed, int reloadSpeed, int ammo, int blankShootRate, int firingRange, int damage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bulletSpeed = bulletSpeed;
        this.ammo = ammo;
        this.firingRange = firingRange;
        this.damage = damage;
    }

    public TankCannon(JSONObject json) {
        this.id = JSONHelper.getValue(json, "id", 1);
        this.name = JSONHelper.getValue(json, "name", "");
        this.price = JSONHelper.getValue(json, "price", 0);
        this.bulletSpeed = JSONHelper.getValue(json, "bulletSpeed", 1);
        this.ammo = JSONHelper.getValue(json, "ammo", 1);
        this.firingRange = JSONHelper.getValue(json, "firingRange", 1);
        this.damage = JSONHelper.getValue(json, "damage", 1);

        try {
            ArrayList<Bullet> bullets = new ArrayList<>();
            JSONArray b = new JSONArray();

            for (int i = 0; i < b.length(); i++) {
                bullets.add(new Bullet((JSONObject) b.get(i)));
            }

            this.bullets = bullets;
        } catch (JSONException e) {
        }
    }

    public void setAmmo(int ammo) {
        this.ammo = Math.max(ammo, 0);
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
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

    public int getAmmo() {
        return ammo;
    }

    public int getFiringRange() {
        return firingRange;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Fires a bullet from the cannon, reducing ammo and applying damage if a tank is hit.
     */
    public void shoot(Map map, CopyOnWriteArrayList<UserData> users, Point bulletInitialPosition, int angle, Consumer<UserData> onTankPenetration) {
        if (ammo <= 0) return;

        Bullet bullet = new Bullet(owner, bulletInitialPosition, bulletSpeed, -angle, firingRange);
        bullets.add(bullet);

        ammo--;
        bullet.fire(map, users, () -> bullets.remove(bullet), user -> onTankPenetration.accept(user));
    }

    /**
     * Returns the JSON representation of the cannon's data.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("price", price);
        json.put("bulletSpeed", bulletSpeed);
        json.put("firingRange", firingRange);
        json.put("ammo", ammo);

        JSONArray bullets = new JSONArray();

        for (Bullet bullet : this.bullets)
            bullets.put(bullet.toJSON());

        json.put("bullets", bullets);

        return json;
    }

    @Override
    public TankCannon clone() throws CloneNotSupportedException {
        return (TankCannon) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        return id == ((TankCannon) obj).id;
    }

    @Override
    public String toString() {
        return "TankCannon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", owner=" + owner +
                ", bullets=" + bullets +
                ", ammo=" + ammo +
                ", firingRange=" + firingRange +
                ", damage=" + damage +
                '}';
    }
}
