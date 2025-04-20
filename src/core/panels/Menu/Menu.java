package core.panels.Menu;

import utils.PanelListener;

import javax.swing.*;

public class Menu {
    MenuPanel scene;
    String worldCode;

    public Menu(PanelListener listener) {
        scene = new MenuPanel();

        scene.getJoinButton().addActionListener(e -> connectToWorld());
    }

    public JPanel getPanel() {
        return scene.getPanel();
    }

    private void connectToWorld() {

    }
}
