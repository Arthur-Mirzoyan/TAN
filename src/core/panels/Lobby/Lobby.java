package core.panels.Lobby;

import network.Server;
import utils.PanelListener;

import javax.swing.*;

public class Lobby {
    LobbyPanel scene;
    Server server;

    public Lobby(PanelListener listener) {
        scene = new LobbyPanel();

        scene.getCreateButton().addActionListener(e -> {
            server = new Server();

            String ip = server.getIp();
            String worldCode = ip.substring(ip.lastIndexOf(".") + 1);

            scene.switchToCreate(worldCode);
        });

        scene.getJoinButton().addActionListener(e -> scene.switchToJoin());
    }

    public JPanel getPanel() {
        return scene.getPanel();
    }
}
