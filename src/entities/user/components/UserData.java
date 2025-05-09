package entities.user.components;

import entities.tank.Tank;
import entities.user.User;
import org.json.JSONException;
import org.json.JSONObject;

public class UserData {
    private String username;
    private String ip;
    private Tank tank;

    public UserData(User user) {
        this.username = user.getUsername();
        this.tank = user.getCurrentTank();
    }

    public UserData(JSONObject json) throws JSONException {
        this.username = json.getString("username");
        this.tank = new Tank(json.getJSONObject("tank"));
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public Tank getTank() {
        return tank;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("username", username);
        json.put("tank", tank.toJSON());

        return json;
    }
}
