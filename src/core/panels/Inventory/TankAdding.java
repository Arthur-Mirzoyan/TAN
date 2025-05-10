package core.panels.Inventory;

import entities.tank.Tank;
import entities.user.User;
import utils.JSONHelper;
import utils.PanelListener;

import javax.swing.*;

/**
 * The {@code TankAdding} class acts as a controller for the {@link TankAddingPanel}.
 * It facilitates the interaction between the user interface and user inventory by enabling
 * the user to add new tanks composed of a hull and cannon.
 */
public class TankAdding {
    TankAddingPanel scene;

    /**
     * Constructs the tank adding interface and handles the logic of checking
     * and adding the tank to the user's inventory.
     *
     * @param listener the panel listener to manage UI navigation
     * @param user     the user whose inventory is being modified
     */
    public TankAdding(PanelListener listener, User user) {
        scene = new TankAddingPanel(user, (hull, cannon) -> {
            Tank tank = new Tank(hull, cannon);
            if (!(user.getInventory().getTanks().contains(tank))) {
                user.getInventory().addTank(tank);
                JOptionPane.showMessageDialog(null, "Tank successfully added");
                JSONHelper.update("src/data/users.json",
                        "users",
                        "username",
                        user.getUsername(),
                        user.toJSON());
            } else
                JOptionPane.showMessageDialog(null, "Tank is already in the inventory");
        });
    }

    /**
     * Returns the panel view of the tank adding interface.
     *
     * @return the tank adding UI panel
     */
    public JPanel getPanel() {
        return scene;
    }
}
