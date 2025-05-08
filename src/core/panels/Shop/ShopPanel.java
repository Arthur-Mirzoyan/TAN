package core.panels.Shop;

import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import utils.CustomComponents;
import utils.JSONHelper;
import utils.Values;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopPanel extends JPanel {
    private JButton hullSelectButton;
    private JButton cannonSelectButton;

    private JPanel viewBox;
    private GridBagConstraints gbc;

    public ShopPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
        add(generateCenterPanel(), BorderLayout.CENTER);
    }

    private JPanel generateCenterPanel() {
        JPanel box = new JPanel();
        hullSelectButton = CustomComponents.button("HULL");
        cannonSelectButton = CustomComponents.button("CANNON");
        viewBox = generateHullScroll();
        gbc = new GridBagConstraints();

        box.setOpaque(false);
        box.setLayout(new GridBagLayout());

        cannonSelectButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        hullSelectButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        hullSelectButton.setBackground(Values.SECONDARY_COLOR);
        hullSelectButton.setForeground(Values.PRIMARY_COLOR);

        gbc.ipadx = 200;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        box.add(hullSelectButton, gbc);
        gbc.gridx = 1;
        box.add(cannonSelectButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        box.add(viewBox, gbc);

        return box;
    }

    private JPanel generateHullScroll() {
        JPanel scrollBox = new JPanel();

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(scrollBox, BoxLayout.X_AXIS));

        ArrayList<TankHull> tankHulls = JSONHelper.parse("src/objects/tankHulls.json", "hulls", json -> new TankHull(json));

        for(TankHull tankHull : tankHulls) {
            JPanel hullCard = tankHull.generateTankHullCard(tankHull);
            imagePanel.add(hullCard);
            imagePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        scrollBox.add(scrollPane);

        return scrollBox;
    }

    private JPanel generateCannonScroll() {
        JPanel scrollBox = new JPanel();

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(scrollBox, BoxLayout.X_AXIS));

        ArrayList<TankCannon> tankCannons = JSONHelper.parse("src/objects/tankCannons.json", "cannons", json -> new TankCannon(json));

        for(TankCannon tankCannon : tankCannons) {
//            JPanel hullCard = tankCannon.generateTankHullCard(tankCannon);
//            imagePanel.add(tankCannon);
            imagePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        scrollBox.add(scrollPane);

        return scrollBox;
    }
}
