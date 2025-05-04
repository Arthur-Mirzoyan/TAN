package core.panels.Game;

import utils.Map;
import entities.tank.Tank;
import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.JSONReader;
import utils.PanelListener;
import utils.Point;

import javax.swing.*;

public class Game {
    GamePanel scene;


    public Game(PanelListener listener) {
        Map map = JSONReader.parseJSON("src/objects/maps.json", "maps", json -> new Map(json)).get(0);

        User user = new User("anun", "pass");
        user.setCurrentTank(
                new Tank(new Point(100, 45),
                        new TankHull(3, "anun", 1000, 5, 10),
                        new TankCannon(5, "anun", 1000, 1, 10, 5, 5, 500),
                        map)
        );

        scene = new GamePanel(map, user,
                new User[]{
                        new User("anun1", "pass"),
                        new User("anun2", "pass"),
                        new User("anun3", "pass"),
                });
    }

    public JPanel getPanel() {
        return scene;
    }
}
