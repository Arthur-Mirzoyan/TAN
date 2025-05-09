package core.panels.Lobby;

import core.exceptions.IPNotFoundException;
import core.exceptions.ServerBindingException;
import core.exceptions.ServerCreationException;
import entities.user.User;
import network.Client;
import network.IPHelper;
import network.Server;
import utils.CustomComponents;
import utils.PanelListener;

import javax.swing.*;
import java.io.IOException;

public class Lobby {
    private LobbyPanel scene;
    private Server server;
    private Client client;
    private User user;

    public Lobby(PanelListener listener, User user) {
        this.scene = new LobbyPanel();
        this.user = user;

        scene.getCreateButton().addActionListener(e -> {
            server = new Server();

            String ip = server.getIp();
            String worldCode = ip.substring(ip.lastIndexOf(".") + 1);

            new Thread(() -> {
                try {
                    server.run();
                } catch (ServerCreationException | ServerBindingException exp) {
                    JOptionPane.showMessageDialog(null, exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }).start();

            try {
                client = new Client(user, ip, Server.PORT);
            } catch (IOException exp) {
                JOptionPane.showMessageDialog(null, "Game couldn't be created.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            scene.setServer(server);
            scene.switchToCreate(worldCode);
        });

        scene.getJoinButton().addActionListener(e -> scene.switchToJoin());
        scene.getJoinWorldButton().addActionListener(e -> joinGame(scene.getWorldJoinIPField().getText()));
        scene.getCreateWorldButton().addActionListener(e -> {
            client.sendJSON(server.getConnectedUsers());
            listener.goToGame(user, server.getClientUser(client), client, server.getConnectedUsers());
        });
    }

    public JPanel getPanel() {
        return scene;
    }

    private void joinGame(String worldCode) {
        try {
            String ip = IPHelper.getLocalIP();
            ip = ip.substring(0, ip.lastIndexOf(".") + 1) + worldCode;

            client = new Client(user, ip, Server.PORT);

            JDialog loadingDialog = CustomComponents.loadingDialog("Waiting for the host to start the game.", () -> {
                client.kill();
            });
            loadingDialog.setVisible(true);


        } catch (IPNotFoundException e) {
            JOptionPane.showMessageDialog(null, "IP Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't connect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
