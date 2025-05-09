package entities.user;

import entities.tank.Tank;
import entities.user.components.Inventory;
import org.json.JSONObject;

public class User {
    private String username;
    private String password;
    private Tank currentTank;
    private Inventory inventory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.inventory = new Inventory(user.inventory);
        this.currentTank = new Tank(user.currentTank);
    }

    public User(JSONObject json) {
        this(json.getString("username"), json.getString("password"));

        this.inventory = new Inventory(json.getJSONObject("inventory"));
        this.currentTank = new Tank(json.getJSONObject("currentTank"));
    }

    public void setCurrentTank(Tank tank) {
        this.currentTank = tank;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Tank getCurrentTank() {
        return currentTank;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("username", username);
        json.put("password", password);
        json.put("currentTank", currentTank == null ? new JSONObject() : currentTank.toJSON());
        json.put("inventory", inventory == null ? new JSONObject() : inventory.toJSON());

        return json;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        User user = (User) obj;

        return username.equals(user.username) && password.equals(user.password);
    }
}
