package core.panels.Game;

import javax.swing.*;

import entities.user.User;
import utils.Map;
import utils.JSONHelper;
import utils.PanelListener;

public class Game {
    GamePanel scene;

    public Game(PanelListener listener, User user) {
        Map map = JSONHelper.parse("src/objects/maps.json", "maps", json -> new Map(json)).get(0);

        // TODO: Handle Tank SpawnPoints

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
