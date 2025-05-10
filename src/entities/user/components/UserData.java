package entities.user.components;

import entities.tank.Tank;
import entities.user.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Lightweight representation of a user during gameplay or networking.
 * Includes username, IP, current tank, and score.
 */
public class UserData {
    private String username;
    private String ip;
    private Tank tank;
    private int score;

    /**
     * Constructs UserData from a {@link User} object.
     */
    public UserData(User user) {
        this.username = user.getUsername();
        this.tank = user.getCurrentTank();
    }

    /**
     * Loads UserData from JSON, including tank state.
     */
    public UserData(JSONObject json) throws JSONException {
        this.username = json.getString("username");
        this.tank = new Tank(json.getJSONObject("tank"));
        this.score = json.getInt("score");
        this.ip = json.getString("ip");
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
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

    /**
     * Returns this user data as a JSON object.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("username", username);
        json.put("tank", tank.toJSON(true, true));
        json.put("score", score);
        json.put("ip", ip);

        return json;
    }

    /**
     * Updates the current tank's position, health, and score.
     *
     * @param tank  updated tank state
     * @param score new score value
     */
    public void updateTankScore(Tank tank, int score) {
        this.tank.setPosition(tank.getPosition());
        this.tank.getHull().setHealth(tank.getHull().getHealth());
        this.score = score;
    }

//    @Override
//    public String toString() {
//        return "UserData{" +
//                "username='" + username + '\'' +
//                ", ip='" + ip + '\'' +
//                ", tank=" + tank +
//                ", score=" + score +
//                '}';
//    }
}
