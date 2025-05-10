package core.panels.Menu;

import entities.user.User;
import utils.CustomComponents;
import utils.Values;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code MenuPanel} class displays the main menu interface of the application.
 * It provides navigation buttons for gameplay, inventory access, and shop entry.
 */
public class MenuPanel extends JPanel {
    User user;

    private JButton playButton;
    private JButton inventoryButton;
    private JButton shopButton;

    /**
     * Constructs a {@code MenuPanel} initialized for a specific user.
     *
     * @param user the currently logged-in user
     */
    public MenuPanel(User user) {
        this.user = user;

        setOpaque(false);
        setLayout(new BorderLayout());
        add(generateButtonBox(), BorderLayout.CENTER);
    }

    /**
     * Returns the "Play" button component.
     *
     * @return the play button
     */
    public JButton getPlayButton() {
        return playButton;
    }

    /**
     * Returns the "Inventory" button component.
     *
     * @return the inventory button
     */
    public JButton getInventoryButton() {
        return inventoryButton;
    }

    /**
     * Returns the "Shop" button component.
     *
     * @return the shop button
     */
    public JButton getShopButton() {
        return shopButton;
    }

    /**
     * Creates and returns the panel containing all menu buttons (Play, Inventory, Shop).
     *
     * @return the button container panel
     */
    private JPanel generateButtonBox() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();


        playButton = CustomComponents.button("Play");
        playButton.setPreferredSize(new Dimension(100, 60));
        playButton.setFont(Values.LARGE_FONT);

        inventoryButton = CustomComponents.button("Inventory");
        inventoryButton.setPreferredSize(new Dimension(100, 60));
        inventoryButton.setFont(Values.LARGE_FONT);

        shopButton = CustomComponents.button("Shop");
        shopButton.setPreferredSize(new Dimension(100, 60));
        shopButton.setFont(Values.LARGE_FONT);

        // Grid
        gbc.ipadx = 200;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        gbc.gridy = 0;
        panel.add(playButton, gbc);

        gbc.gridy = 1;
        panel.add(inventoryButton, gbc);

        gbc.gridy = 2;
        panel.add(shopButton, gbc);

        return panel;
    }
}
