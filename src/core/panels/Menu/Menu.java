package core.panels.Menu;

import entities.user.User;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;

/**
 * The {@code Menu} class manages the main menu screen of the application.
 * It enables navigation to gameplay, inventory, shop, and volume settings.
 */
public class Menu {
    MenuPanel scene;

    /**
     * Constructs a {@code Menu} controller for the main menu screen.
     *
     * @param listener the panel listener to manage view navigation
     * @param user     the currently logged-in user
     */
    public Menu(PanelListener listener, User user) {
        scene = new MenuPanel(user);

        scene.getPlayButton().addActionListener(e -> listener.goTo(Panels.LOBBY, user));
        scene.getInventoryButton().addActionListener(e -> listener.goTo(Panels.INVENTORY, user));
        scene.getShopButton().addActionListener(e -> listener.goTo(Panels.SHOP, user));
        scene.getPlayButton().addActionListener(e -> {
            if (user.getCurrentTank().getId().isBlank())
                JOptionPane.showMessageDialog(null, "Please select tank before playing.", "Attention", JOptionPane.INFORMATION_MESSAGE);
            else
                listener.goTo(Panels.LOBBY, user);
        });
    }

    /**
     * Returns the main menu panel.
     *
     * @return the menu panel as a {@code JPanel}
     */
    public JPanel getPanel() {
        return scene;
    }
}
