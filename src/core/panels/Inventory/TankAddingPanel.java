package core.panels.Inventory;

import entities.tank.components.TankCannon;
import entities.tank.components.TankCard;
import entities.tank.components.TankHull;
import entities.user.User;
import utils.CustomComponents;
import utils.Values;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The {@code TankAddingPanel} class provides a graphical interface for
 * selecting a tank hull and cannon, allowing the user to build and add tanks.
 */
public class TankAddingPanel extends JPanel {
    private final User user;

    private JScrollPane hullScrollPane;
    private JScrollPane cannonScrollPane;
    private JButton addButton;

    private TankHull bufferHull;
    private TankCannon bufferCannon;

    /**
     * Constructs the panel with scrollable lists of available hulls and cannons.
     *
     * @param user   the user whose inventory provides hull and cannon data
     * @param action the callback to execute when a tank is added
     */
    public TankAddingPanel(User user, BiConsumer<TankHull, TankCannon> action) {
        this.user = user;
        setOpaque(false);
        setLayout(new GridBagLayout());
        add(generateCenterPanel(action));
    }

    private JPanel generateCenterPanel(BiConsumer<TankHull, TankCannon> action) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(0xD2B48C));
        panel.setPreferredSize(new Dimension(800, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);

        JLabel hullLabel = new JLabel("Hull");
        hullLabel.setFont(Values.EXTRA_LARGE_FONT);

        JLabel cannonLabel = new JLabel("Cannon");
        cannonLabel.setFont(Values.EXTRA_LARGE_FONT);

        hullScrollPane = generateHullScroll(tankHull -> {
            bufferHull = (TankHull) tankHull;
            panel.repaint();
        });

        cannonScrollPane = generateCannonScroll(tankCannon -> {
            bufferCannon = (TankCannon) tankCannon;
            panel.repaint();
        });

        addButton = CustomComponents.button("Add");
        addButton.addActionListener(e -> action.accept(bufferHull, bufferCannon));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(hullLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(cannonLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0;
        panel.add(hullScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(cannonScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(addButton, gbc);

        return panel;
    }

    private JScrollPane generateHullScroll(Consumer<Object> action) {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));

        ArrayList<TankHull> tankHulls = user.getInventory().getHulls();

        for (TankHull tankHull : tankHulls) {
            TankCard hullCard = new TankCard(tankHull, bufferHull == tankHull ? "Selected" : "Select", 150, 150, 75, 100);
            hullCard.getButton().addActionListener(e -> action.accept(tankHull));
            hullCard.setMaximumSize(new Dimension(150, 300));
            cardPanel.add(hullCard);
            cardPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBackground(new Color(0xD2B48C));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(150, 300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private JScrollPane generateCannonScroll(Consumer<Object> action) {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));

        ArrayList<TankCannon> tankCannons = user.getInventory().getCannons();

        for (TankCannon tankCannon : tankCannons) {
            TankCard cannonCard = new TankCard(tankCannon, bufferCannon == tankCannon ? "Selected" : "Select", 150, 150, 37, 75);
            cannonCard.getButton().addActionListener(e -> action.accept(tankCannon));
            cannonCard.setMaximumSize(new Dimension(150, 300));
            cardPanel.add(cannonCard);
            cardPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBackground(new Color(0xD2B48C));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(150, 300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    /**
     * Returns the "Add" button component.
     *
     * @return the add button
     */
    public JButton getAddButton() {
        return addButton;
    }
}
