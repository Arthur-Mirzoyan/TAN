package core.panels.Inventory;

import utils.PanelListener;
import javax.swing.*;

public class Inventory {
    InventoryPanel scene;

    public Inventory(PanelListener listener) {
        scene = new InventoryPanel();
    }

    public JPanel getPanel() {
        return scene;
    }
}
