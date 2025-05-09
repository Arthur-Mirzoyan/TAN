package core.panels.Shop;

import entities.user.User;
import utils.PanelListener;

import javax.swing.*;


public class Shop {
    ShopPanel scene;

    public Shop(PanelListener listener, User user) {
        scene = new ShopPanel();
    }

    public JPanel getPanel() {
        return scene;
    }
}
