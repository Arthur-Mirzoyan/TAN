package core.panels.Game;

import entities.Map;
import entities.user.User;
import utils.JSONReader;
import utils.PanelListener;

import javax.swing.*;

public class Game {
    GamePanel scene;


    public Game(PanelListener listener) {
        Map map = JSONReader.parseJSON("src/objects/maps.json", "maps",
                json -> new Map(json)).get(0);


        scene = new GamePanel(map, new User("anun", "pass"),
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
