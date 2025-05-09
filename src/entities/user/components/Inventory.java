package entities.user.components;

import entities.tank.Tank;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONHelper;

import java.util.ArrayList;

public class Inventory {
    private int money;
    private ArrayList<TankCannon> cannons;
    private ArrayList<TankHull> hulls;
    private ArrayList<Tank> tanks;

    public Inventory() {
        this.money = 0;
        this.cannons = new ArrayList<>();
        this.hulls = new ArrayList<>();
        this.tanks = new ArrayList<>();
    }

    public Inventory(Inventory inventory) {
        this.money = inventory.money;
        this.cannons = inventory.cannons;
        this.hulls = inventory.hulls;
        this.tanks = new ArrayList<>();

        for (Tank tank : inventory.tanks) {
            this.tanks.add(new Tank(tank));
        }
    }

    public Inventory(JSONObject json) {
        this.money = JSONHelper.getValue(json, "money", 1000000);
        this.cannons = new ArrayList<>();
        this.hulls = new ArrayList<>();
        this.tanks = new ArrayList<>();

        JSONArray jsonCannons = JSONHelper.getValue(json, "cannons", new JSONArray());
        JSONArray jsonHulls = JSONHelper.getValue(json, "hulls", new JSONArray());
        JSONArray jsonTanks = JSONHelper.getValue(json, "tanks", new JSONArray());

        for (Object obj : jsonCannons) this.cannons.add(new TankCannon((JSONObject) obj));
        for (Object obj : jsonHulls) this.hulls.add(new TankHull((JSONObject) obj));
        for (Object obj : jsonTanks) this.tanks.add(new Tank((JSONObject) obj));
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<TankCannon> getCannons() {
        return cannons;
    }

    public ArrayList<TankHull> getHulls() {
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
        this.cannons.add(cannon);
    }

    public void addHull(TankHull hull) {
        this.hulls.add(hull);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        JSONArray cannons = new JSONArray();
        JSONArray hulls = new JSONArray();
        JSONArray tanks = new JSONArray();

        this.cannons.forEach(cannon -> cannons.put(cannon.toJSON()));
        this.hulls.forEach(hull -> hulls.put(hull.toJSON()));
        this.tanks.forEach(tank -> tanks.put(tank.toJSON()));

        json.put("money", money);
        json.put("cannons", cannons);
        json.put("hulls", hulls);
        json.put("tanks", tanks);

        return json;
    }
}
