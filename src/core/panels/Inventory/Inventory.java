package core.panels.Inventory;

import entities.user.User;
import utils.PanelListener;
import javax.swing.*;

public class Inventory {
    InventoryPanel scene;

    public Inventory(PanelListener listener, User user) {
        scene = new InventoryPanel();
    }

    public JPanel getPanel() {
        return scene;
    }
}
