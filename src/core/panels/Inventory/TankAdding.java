package core.panels.Inventory;

import entities.tank.Tank;
import entities.user.User;
import utils.PanelListener;

import javax.swing.*;

public class TankAdding {
    TankAddingPanel scene;

    public TankAdding(PanelListener listener, User user) {
        scene = new TankAddingPanel(user, (hull, cannon) -> {
            Tank tank = new Tank(hull, cannon);
            if (!(user.getInventory().getTanks().contains(tank))) {
                user.getInventory().addTank(tank);
                JOptionPane.showMessageDialog(null, "Tank successfully added");
            } else
                JOptionPane.showMessageDialog(null, "Tank is already in the inventory");
        });
    }

    public JPanel getPanel() {
        return scene;
    }
}
