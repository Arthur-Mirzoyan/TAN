package core.panels.Shop;

import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.JSONHelper;
import utils.PanelListener;

import javax.swing.*;


public class Shop {
    ShopPanel scene;

    public Shop(PanelListener listener, User user) {
        scene = new ShopPanel(user, obj -> buy(user, obj));

        scene.getHullSelectButton().addActionListener(e -> scene.switchToHull(obj -> buy(user, obj)));
        scene.getCannonSelectButton().addActionListener(e -> scene.switchToCannon(obj -> buy(user, obj)));
    }

    public JPanel getPanel() {
        return scene;
    }

    private void buy(User user, Object obj) {
        if (obj instanceof TankCannon) {
            TankCannon tankCannon = (TankCannon) obj;
            if (!(user.getInventory().getCannons().contains(tankCannon))) {
                if (user.getInventory().getMoney() >= tankCannon.getPrice()) {
                    user.getInventory().addCannon(tankCannon);
                    user.getInventory().setMoney(user.getInventory().getMoney() - tankCannon.getPrice());
                    JOptionPane.showMessageDialog(null, "Cannon purchased successfully!");
                    updateUserData(user);
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough money.");
                }
            } else
                JOptionPane.showMessageDialog(null, "You already have this cannon.");
        } else if (obj instanceof TankHull) {
            TankHull tankHull = (TankHull) obj;
            if (!(user.getInventory().getHulls().contains(tankHull))) {
                if (user.getInventory().getMoney() >= tankHull.getPrice()) {
                    user.getInventory().addHull(tankHull);
                    user.getInventory().setMoney(user.getInventory().getMoney() - tankHull.getPrice());
                    JOptionPane.showMessageDialog(null, "Hull purchased successfully!");
                    updateUserData(user);
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough money.");
                }
            } else
                JOptionPane.showMessageDialog(null, "You already have this hull.");
        }
    }

    private void updateUserData(User user) {
        JSONHelper.update("src/data/users.json",
                "users",
                "username",
                user.getUsername(),
                user.toJSON());
    }
}
