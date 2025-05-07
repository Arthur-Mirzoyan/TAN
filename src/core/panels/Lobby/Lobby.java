package core.panels.Lobby;

import entities.user.User;
import network.Server;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;

public class Lobby {
    LobbyPanel scene;
    Server server;

    public Lobby(PanelListener listener, User user) {
        scene = new LobbyPanel();

        scene.getCreateButton().addActionListener(e -> {
            server = new Server();

            String ip = server.getIp();
            String worldCode = ip.substring(ip.lastIndexOf(".") + 1);

            scene.switchToCreate(worldCode);
        });

        scene.getJoinButton().addActionListener(e -> scene.switchToJoin());
        scene.getJoinWorldButton().addActionListener(e -> listener.goTo(Panels.GAME, user));
    }

    public JPanel getPanel() {
        return scene;
    }
}
