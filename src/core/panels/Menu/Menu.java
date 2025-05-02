package core.panels.Menu;

import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    MenuPanel scene;

    public Menu(PanelListener listener) {
        scene = new MenuPanel();

//        scene.getPlayButton().addActionListener(e ->);
        scene.getInventoryButton().addActionListener(e -> listener.goTo(Panels.INVENTORY));
        scene.getShopButton().addActionListener(e -> listener.goTo(Panels.SHOP));

        scene.getVolumeConfiguratorButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton button = scene.getVolumeConfiguratorButton();
                Icon current = button.getIcon();

                if (current.equals(scene.getVolumeIcon())) {
                    button.setIcon(scene.getMuteIcon());
                } else {
                    button.setIcon(scene.getVolumeIcon());
                }
            }
        });
    }

    public JPanel getPanel() {
        return scene;
    }
}
