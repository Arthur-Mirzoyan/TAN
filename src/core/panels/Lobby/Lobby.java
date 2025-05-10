package core.panels.Lobby;

import core.exceptions.IPNotFoundException;
import core.exceptions.ServerBindingException;
import core.exceptions.ServerCreationException;
import entities.user.User;
import network.Client;
import network.IPHelper;
import network.Server;
import utils.CustomComponents;
import utils.CustomThread;
import utils.PanelListener;

import javax.swing.*;
import java.io.IOException;

/**
 * The {@code Lobby} class handles the logic for creating and joining multiplayer sessions.
 * It manages client-server connections and controls the interaction between the user and the lobby interface.
 */
public class Lobby {
    private PanelListener panelListener;
    private LobbyPanel scene;
    private Server server;
    private Client client;
    private User user;

    /**
     * Constructs a new lobby controller, handling the multiplayer game session UI and logic.
     *
     * @param listener panel listener to manage navigation
     * @param user     the current user
     */
    public Lobby(PanelListener listener, User user) {
        this.panelListener = listener;
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
        scene.getCreateWorldButton().addActionListener(e -> createGame());
    }

    /**
     * Returns the lobby panel for UI display.
     *
     * @return the panel representing the lobby
     */
    public JPanel getPanel() {
        return scene;
    }

    private void createGame() {
        server.broadcast("START", null);
        server.sendJSON();
        client.sendJSON(server.getConnectedUsers());
        panelListener.goToGame(user, server.getClientUser(client), client, server.getConnectedUsers(), server);
    }

    /**
     * Handles joining an existing world using a provided world code.
     *
     * @param worldCode the last octet of the host's IP address
     */
    private void joinGame(String worldCode) {
        try {
            String ip = IPHelper.getLocalIP();
            ip = ip.substring(0, ip.lastIndexOf(".") + 1) + worldCode;

            client = new Client(user, ip, Server.PORT);

            CustomThread clientListener = new CustomThread(500);
            new Thread(() -> {
                client.listenForServerMessages();
            }).start();

            JDialog loadingDialog = CustomComponents.loadingDialog("Waiting for the host to start the game.", () -> {
                client.kill();
            });

            new Thread(() -> {
                loadingDialog.setVisible(true);
            }).start();

            clientListener.run(() -> {
                if (client.getIsReady()) {
                    loadingDialog.dispose();
                    panelListener.goToGame(user, client.getUserData(), client, client.getConnectedUsers(), server);
                    clientListener.stop();
                }
            });


        } catch (IPNotFoundException e) {
            JOptionPane.showMessageDialog(null, "IP Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't connect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
