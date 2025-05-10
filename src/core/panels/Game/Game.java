package core.panels.Game;

import entities.tank.Tank;
import entities.user.User;
import entities.user.components.UserData;
import network.Client;
import utils.JSONHelper;
import utils.Map;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code Game} class contains the core logic and state of the game session.
 * It manages gameplay data, progression, and reset operations.
 */
public class Game {
    private GamePanel scene;
    private ArrayList<Tank> tanks;

    public Game(PanelListener listener, User user, UserData currentUserData, Client client, CopyOnWriteArrayList<UserData> connectedUsers) {
        Map map = JSONHelper.parse("src/objects/maps.json", "maps", json -> new Map(json)).get(1);

        scene = new GamePanel(currentUserData, connectedUsers, map, () -> client.sendJSON(connectedUsers));

        scene.getExitButton().addActionListener(e -> listener.goTo(Panels.MENU, user));
    }

    public JPanel getPanel() {
        return scene;
    }
}
