package core.panels.Lobby;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import entities.user.User;
import utils.CustomComponents;
import utils.Values;

public class LobbyPanel {
    private JPanel panel;
    private JList<User> connectedUsersList;
    private JTextField worldJoinIPField;
    private JButton joinButton;
    private JButton createButton;
    private JButton joinWorldButton;
    private JButton createWorldButton;

    private JPanel viewBox;
    private GridBagConstraints gbc;

    public LobbyPanel() {
        panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

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

        panel.add(box, BorderLayout.CENTER);
    }

    public void setConnectedUsersList(ArrayList<User> connectedUsers) {
        connectedUsersList = new JList((User[]) connectedUsers.toArray());
    }

    public JPanel getPanel() {
        return panel;
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

    public void switchToCreate(String code) {
        createButton.setBackground(Values.SECONDARY_COLOR);
        createButton.setForeground(Values.PRIMARY_COLOR);
        joinButton.setBackground(Values.PRIMARY_COLOR);
        joinButton.setForeground(Values.TERTIARY_COLOR);

        JPanel parent = (JPanel) viewBox.getParent();
        parent.remove(viewBox);
        viewBox = generateWorldCreationBox(code);
        parent.add(viewBox, gbc);
        parent.revalidate();
        parent.repaint();
    }

    public void switchToJoin() {
        createButton.setBackground(Values.PRIMARY_COLOR);
        createButton.setForeground(Values.TERTIARY_COLOR);
        joinButton.setBackground(Values.SECONDARY_COLOR);
        joinButton.setForeground(Values.PRIMARY_COLOR);

        JPanel parent = (JPanel) viewBox.getParent();
        parent.remove(viewBox);
        viewBox = generateWorldJoinBox();
        parent.add(viewBox, gbc);
        parent.revalidate();
        parent.repaint();
    }

    private JPanel generateWorldCreationBox(String code) {
        JPanel box = new JPanel();

        box.setBackground(Values.SECONDARY_COLOR);
        box.setLayout(new BorderLayout(0, 10));
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel worldCodeLabel = CustomComponents.label("WORLD CODE:");
        JLabel worldCode = CustomComponents.label(code);
        JLabel usersConnectedLabel = CustomComponents.label("Users connected:");

        usersConnectedLabel.setFont(Values.SMALL_FONT);

        User[] testUsers = new User[5];
        testUsers[0] = new User("user 1", "pass 1");
        testUsers[1] = new User("user 2", "pass 2");
        testUsers[2] = new User("user 3", "pass 3");

        connectedUsersList = new JList(testUsers);
        connectedUsersList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(connectedUsersList);

        JPanel worldCodeBox = new JPanel();
        worldCodeBox.setOpaque(false);
        worldCodeBox.setLayout(new BorderLayout());
        worldCodeBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        worldCodeBox.add(worldCodeLabel, BorderLayout.WEST);
        worldCodeBox.add(worldCode, BorderLayout.EAST);

        box.add(worldCodeBox, BorderLayout.NORTH);
        box.add(usersConnectedLabel, BorderLayout.CENTER);
        box.add(scrollPane, BorderLayout.SOUTH);

        return box;
    }

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
}
