package core.panels.Inventory;

import entities.user.User;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;

/**
 * The {@code Inventory} class represents a collection of items owned by the player.
 * It supports adding and removing items as part of gameplay.
 */
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
