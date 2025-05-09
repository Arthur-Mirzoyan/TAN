package core.panels.Menu;

import entities.user.User;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    MenuPanel scene;

    public Menu(PanelListener listener, User user) {
        scene = new MenuPanel();

        scene.getPlayButton().addActionListener(e -> listener.goTo(Panels.LOBBY, user));
        scene.getInventoryButton().addActionListener(e -> listener.goTo(Panels.INVENTORY, user));
        scene.getShopButton().addActionListener(e -> listener.goTo(Panels.SHOP, user));
        scene.getPlayButton().addActionListener(e -> {
            if (user.getCurrentTank().getId().isBlank())
                JOptionPane.showMessageDialog(null, "Please select tank before playing.", "Attention", JOptionPane.INFORMATION_MESSAGE);
            else
                listener.goTo(Panels.LOBBY, user);
        });

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
