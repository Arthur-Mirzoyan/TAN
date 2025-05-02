package core.panels.Shop;

import utils.PanelListener;
import javax.swing.*;


public class Shop {
    ShopPanel scene;

    public Shop(PanelListener listener) {
        scene = new ShopPanel();
    }

    public JPanel getPanel() {
        return scene;
    }
}
