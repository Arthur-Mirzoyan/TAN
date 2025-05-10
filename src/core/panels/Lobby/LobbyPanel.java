package core.panels.Lobby;

import entities.user.components.UserData;
import network.Server;
import utils.CustomComponents;
import utils.CustomThread;
import utils.Values;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code LobbyPanel} class defines the GUI for the multiplayer lobby screen.
 * It allows users to either create a new game session or join an existing one
 * using a world code. It also displays connected players in the lobby.
 */
public class LobbyPanel extends JPanel {
    private JPanel viewBox;
    private GridBagConstraints gbc;

    private JTextField worldJoinIPField;
    private JButton joinButton;
    private JButton createButton;
    private JButton joinWorldButton;
    private JButton createWorldButton = CustomComponents.button("Start");

    private Server server;

    /**
     * Constructs the lobby panel and sets up the interface with "Join" and "Create" options.
     */
    public LobbyPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel box = new JPanel();
        joinButton = CustomComponents.button("JOIN");
        createButton = CustomComponents.button("CREATE");
        viewBox = generateWorldJoinBox();
        gbc = new GridBagConstraints();

        box.setOpaque(false);
        box.setLayout(new GridBagLayout());

        createButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        joinButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        joinButton.setBackground(Values.SECONDARY_COLOR);
        joinButton.setForeground(Values.PRIMARY_COLOR);

        gbc.ipadx = 200;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        box.add(joinButton, gbc);
        gbc.gridx = 1;
        box.add(createButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        box.add(viewBox, gbc);

        add(box, BorderLayout.CENTER);
    }

    /**
     * Sets the server associated with this lobby.
     *
     * @param server the server instance
     */
    public void setServer(Server server) {
        this.server = server;
    }

    public JTextField getWorldJoinIPField() {
        return worldJoinIPField;
    }

    public JButton getJoinButton() {
        return joinButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getJoinWorldButton() {
        return joinWorldButton;
    }

    public JButton getCreateWorldButton() {
        return createWorldButton;
    }

    /**
     * Switches the view to the "Create Game" state and displays the connected users and world code.
     *
     * @param code the world code generated from server IP
     */
    public void switchToCreate(String code) {
        createButton.setBackground(Values.SECONDARY_COLOR);
        createButton.setForeground(Values.PRIMARY_COLOR);
        joinButton.setBackground(Values.PRIMARY_COLOR);
        joinButton.setForeground(Values.TERTIARY_COLOR);

        CustomThread connectedUsersListThread = new CustomThread(1000);
        connectedUsersListThread.run(() -> {
            repaintViewBox(generateWorldCreationBox(code));
        });
    }

    /**
     * Switches the view to the "Join Game" state, allowing the user to input a world code.
     */
    public void switchToJoin() {
        createButton.setBackground(Values.PRIMARY_COLOR);
        createButton.setForeground(Values.TERTIARY_COLOR);
        joinButton.setBackground(Values.SECONDARY_COLOR);
        joinButton.setForeground(Values.PRIMARY_COLOR);

        repaintViewBox(generateWorldJoinBox());
    }

    /**
     * Replaces the current center view with a new panel.
     *
     * @param newViewBox the new panel to display
     */
    private void repaintViewBox(JPanel newViewBox) {
        JPanel parent = (JPanel) viewBox.getParent();
        parent.remove(viewBox);
        viewBox = newViewBox;
        parent.add(viewBox, gbc);
        parent.revalidate();
        parent.repaint();
    }

    /**
     * Generates the panel shown when creating a world, displaying the world code and connected users.
     *
     * @param code the world code for the session
     * @return the creation panel
     */
    private JPanel generateWorldCreationBox(String code) {
        JPanel box = new JPanel();

        box.setBackground(Values.SECONDARY_COLOR);
        box.setLayout(new BorderLayout(0, 10));
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel worldCodeLabel = CustomComponents.label("WORLD CODE:");
        JLabel worldCode = CustomComponents.label(code);
        JPanel connectedUsersList = createConnectedUsersPanel();

        connectedUsersList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Users connected"));

        JPanel box1 = new JPanel();
        box1.setOpaque(false);
        box1.setLayout(new BorderLayout());
        box1.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        box1.add(worldCodeLabel, BorderLayout.WEST);
        box1.add(worldCode, BorderLayout.EAST);

        JPanel box2 = new JPanel();
        box2.setOpaque(false);
        box2.setLayout(new BorderLayout());
        box2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        box.add(box1, BorderLayout.NORTH);
        box.add(box2, BorderLayout.CENTER);
        box.add(connectedUsersList, BorderLayout.SOUTH);

        return box;
    }

    /**
     * Generates the input panel for joining a world using an IP code.
     *
     * @return the join input panel
     */
    private JPanel generateWorldJoinBox() {
        JPanel box = new JPanel();

        box.setBackground(Values.SECONDARY_COLOR);
        box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        worldJoinIPField = CustomComponents.numericInputBox();
        joinWorldButton = CustomComponents.button("Join");

        box.add(worldJoinIPField);
        box.add(Box.createRigidArea(new Dimension(5, 0)));
        box.add(joinWorldButton);

        return box;
    }

    /**
     * Creates a scrollable panel displaying all users currently connected to the lobby.
     *
     * @return a panel containing usernames and a "Start" button
     */
    private JPanel createConnectedUsersPanel() {
        JPanel box = new JPanel(new GridBagLayout());
        CopyOnWriteArrayList<UserData> users = server.getConnectedUsers();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        if (users != null) {
            for (UserData user : users) {
                JLabel label = new JLabel(user.getUsername(), SwingConstants.CENTER);
                label.setFont(Values.MEDIUM_FONT);
                box.add(label, gbc);
                gbc.gridy++;
            }

            if (users.size() > 1) box.add(createWorldButton, gbc);
        }

        return box;
    }

}
