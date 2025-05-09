package core.panels.Inventory;

import entities.user.User;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;

public class Inventory {
    InventoryPanel scene;

    public Inventory(PanelListener listener, User user) {
        scene = new InventoryPanel(user);

        scene.getAddNewTankButton().addActionListener(e -> listener.goTo(Panels.TANK_ADDING, user));
    }

    public JPanel getPanel() {
        return scene;
    }
}
