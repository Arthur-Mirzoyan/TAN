package core.panels.Game;

import entities.user.User;
import entities.user.components.UserData;
import network.Client;
import network.Server;
import utils.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code Game} class contains the core logic and state of the game session.
 * It manages gameplay data, progression, and reset operations.
 */
public class Game {
    private GamePanel scene;

    public Game(PanelListener listener, User user, UserData currentUserData, Client client, CopyOnWriteArrayList<UserData> connectedUsers, Server server) {
        Map map = JSONHelper.parse("src/objects/maps.json", "maps", json -> new Map(json)).get(1);

        if (server != null) {
            ArrayList<Point> spawnPoints = map.getSpawnPoints();
            int k = 0;

            for (UserData userData : connectedUsers) {
                if (k >= spawnPoints.size()) break;

                userData.getTank().setPosition(spawnPoints.get(k));
                ++k;
            }

            server.sendJSON();
        }

        scene = new GamePanel(currentUserData, connectedUsers, map, () -> client.listenForServerMessages(), () -> {
            // TODO: client.sendJSON();
        });

        scene.getExitButton().addActionListener(e -> {
            if (server != null) {
                server.kill();
            }
            listener.goTo(Panels.MENU, user);
        });
    }

    public JPanel getPanel() {
        return scene;
    }
}
