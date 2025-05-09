package core.panels.Shop;

import entities.tank.components.TankCannon;
import entities.tank.components.TankCard;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.CustomComponents;
import utils.JSONHelper;
import utils.Values;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ShopPanel extends JPanel {
    private final User user;

    private JButton hullSelectButton;
    private JButton cannonSelectButton;

    private JScrollPane viewBox;
    private GridBagConstraints gbc;

    public ShopPanel(User user, Consumer<Object> action) {
        this.user = user;
        setOpaque(false);
        setLayout(new BorderLayout());
        add(generateCenterPanel(action), BorderLayout.CENTER);
    }

    private JPanel generateCenterPanel(Consumer<Object> action) {
        JPanel box = new JPanel();

        hullSelectButton = CustomComponents.button("HULL");
        cannonSelectButton = CustomComponents.button("CANNON");
        viewBox = generateHullScroll(action);
        gbc = new GridBagConstraints();

        box.setOpaque(false);
        box.setLayout(new GridBagLayout());

        cannonSelectButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        hullSelectButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        hullSelectButton.setBackground(Values.SECONDARY_COLOR);
        hullSelectButton.setForeground(Values.PRIMARY_COLOR);

        gbc.ipadx = 300;
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

    private JScrollPane generateHullScroll(Consumer<Object> action) {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        ArrayList<TankHull> tankHulls = JSONHelper.parse("src/objects/tankHulls.json", "hulls", json -> new TankHull(json));

        for (TankHull tankHull : tankHulls) {
            TankCard hullCard = new TankCard(tankHull, "Buy", 200, 200, 150, 200);
            hullCard.getButton().addActionListener(e -> action.accept(tankHull));
            cardPanel.add(hullCard);
            cardPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBackground(new Color(0xD2B48C));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(300, 420));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private JScrollPane generateCannonScroll(Consumer<Object> action) {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        ArrayList<TankCannon> tankCannons = JSONHelper.parse("src/objects/tankCannons.json", "cannons", json -> new TankCannon(json));

        for (TankCannon tankCannon : tankCannons) {
            TankCard cannonCard = new TankCard(tankCannon, "Buy", 200, 200, 75, 200);
            cannonCard.getButton().addActionListener(e -> action.accept(tankCannon));
            cardPanel.add(cannonCard);
            cardPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBackground(new Color(0xD2B48C));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(300, 420));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    public void switchToCannon(Consumer<Object> action) {
        cannonSelectButton.setBackground(Values.SECONDARY_COLOR);
        cannonSelectButton.setForeground(Values.PRIMARY_COLOR);
        hullSelectButton.setBackground(Values.PRIMARY_COLOR);
        hullSelectButton.setForeground(Values.TERTIARY_COLOR);

        JPanel parent = (JPanel) viewBox.getParent();
        parent.remove(viewBox);
        viewBox = generateCannonScroll(action);
        parent.add(viewBox, gbc);
        parent.revalidate();
        parent.repaint();
    }

    public void switchToHull(Consumer<Object> action) {
        cannonSelectButton.setBackground(Values.PRIMARY_COLOR);
        cannonSelectButton.setForeground(Values.TERTIARY_COLOR);
        hullSelectButton.setBackground(Values.SECONDARY_COLOR);
        hullSelectButton.setForeground(Values.PRIMARY_COLOR);

        JPanel parent = (JPanel) viewBox.getParent();
        parent.remove(viewBox);
        viewBox = generateHullScroll(action);
        parent.add(viewBox, gbc);
        parent.revalidate();
        parent.repaint();
    }

    public JButton getHullSelectButton() {
        return hullSelectButton;
    }

    public JButton getCannonSelectButton() {
        return cannonSelectButton;
    }
}
