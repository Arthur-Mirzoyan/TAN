package core.panels.Game;

import entities.tank.Tank;
import entities.user.User;
import entities.user.components.UserData;
import network.Client;
import network.Server;
import utils.JSONHelper;
import utils.Map;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private GamePanel scene;
    private ArrayList<Tank> tanks;

    public Game(PanelListener listener, User user, UserData currentUserData, Client client, CopyOnWriteArrayList<UserData> connectedUsers, Server server) {
        Map map = JSONHelper.parse("src/objects/maps.json", "maps", json -> new Map(json)).get(1);

        scene = new GamePanel(currentUserData, connectedUsers, map, () -> client.sendJSON(connectedUsers));

        scene.getExitButton().addActionListener(e -> {
            if (server != null) server.kill();
            listener.goTo(Panels.MENU, user);
        });
    }

    public JPanel getPanel() {
        return scene;
    }
}
