package core.panels.Game;

import utils.Map;
import entities.tank.Tank;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.Map;
import utils.JSONHelper;
import utils.PanelListener;

import javax.swing.*;
import java.util.ArrayList;

public class Game {
    GamePanel scene;

    public ArrayList<Tank> tankList;
    //should be properly implemented and placed correctly
    //this one is just a test version.

    public Game(PanelListener listener, User user) {
        Map map = JSONHelper.parse("src/objects/maps.json", "maps", json -> new Map(json)).get(0);

        tankList = new ArrayList<>();

        User user1 = new User("anun", "pass");
        user1.setCurrentTank(
                new Tank(map.getSpawnPoint(0),
                        new TankHull(3, "anun", 1000, 5, 10),
                        new TankCannon(5, "anun", 1000, 1, 10, 5, 5, 500),
                        map,
                        user1)
        );
        tankList.add(user1.getCurrentTank());

        User user2 = new User("anun", "pass");
        user2.setCurrentTank(
                new Tank(map.getSpawnPoint(1),
                        new TankHull(3, "anun", 1000, 5, 10),
                        new TankCannon(5, "anun", 1000, 1, 10, 5, 5, 500),
                        map, user2)
        );
        tankList.add(user2.getCurrentTank());

        User user3 = new User("anun", "pass");
        user3.setCurrentTank(
                new Tank(map.getSpawnPoint(2),
                        new TankHull(3, "anun", 1000, 5, 10),
                        new TankCannon(5, "anun", 1000, 1, 10, 5, 5, 500),
                        map, user3)
        );
        tankList.add(user3.getCurrentTank());

        User user4 = new User("anun", "pass");
        user4.setCurrentTank(
                new Tank(map.getSpawnPoint(3),
                        new TankHull(3, "anun", 1000, 5, 10),
                        new TankCannon(5, "anun", 1000, 1, 10, 5, 5, 500),
                        map, user4)
        );
        tankList.add(user4.getCurrentTank());

        scene = new GamePanel(map, user1,
                new User[]{
                        user1,
                        user2,
                        user3,
                        user4,
                });
    }

    public JPanel getPanel() {
        return scene;
    }
}
