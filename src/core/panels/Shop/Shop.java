package core.panels.Shop;

import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.JSONHelper;
import utils.PanelListener;

import javax.swing.*;

/**
 * The {@code Shop} class serves as the logic controller for the shop UI.
 * It allows users to purchase {@link TankHull} and {@link TankCannon} components if they have enough currency.
 */
public class Shop {
    ShopPanel scene;

    /**
     * Constructs the shop interface and sets up event handling for purchasing items.
     *
     * @param listener panel listener for navigation
     * @param user     the current user interacting with the shop
     */
    public Shop(PanelListener listener, User user) {
        scene = new ShopPanel(user, obj -> buy(user, obj));

        scene.getHullSelectButton().addActionListener(e -> scene.switchToHull(obj -> buy(user, obj)));
        scene.getCannonSelectButton().addActionListener(e -> scene.switchToCannon(obj -> buy(user, obj)));
    }

    /**
     * Returns the UI panel for the shop.
     *
     * @return the shop panel
     */
    public JPanel getPanel() {
        return scene;
    }

    /**
     * Attempts to purchase a {@code TankCannon} or {@code TankHull}, checking for duplicates and available funds.
     *
     * @param user the current user
     * @param obj  the item to purchase
     */
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
