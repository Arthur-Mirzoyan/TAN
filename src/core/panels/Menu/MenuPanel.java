package core.panels.Menu;


import utils.CustomComponents;
import utils.Values;

import javax.swing.*;

public class MenuPanel {
    private JPanel panel;
    private JTextField worldCodeField;
    private JButton joinButton;

    public MenuPanel() {
        panel = new JPanel();
        
        panel.setOpaque(false);

        worldCodeField = CustomComponents.inputBox();
        joinButton = CustomComponents.button("Join");

        panel.add(worldCodeField);
        panel.add(joinButton);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getWorldCodeField() {
        return worldCodeField;
    }

    public JButton getJoinButton() {
        return joinButton;
    }
}
