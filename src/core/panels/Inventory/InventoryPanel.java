package core.panels.Inventory;

import entities.tank.Tank;
import entities.tank.components.TankCard;
import entities.user.User;
import utils.CustomComponents;
import utils.JSONHelper;
import utils.Values;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPanel extends JPanel {
    private final User user;

    private JButton addNewTankButton;

    public InventoryPanel(User user) {
        this.user = user;
        setOpaque(false);
        setLayout(new BorderLayout());
        add(header(), BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(generateTankScroll());

        add(wrapper, BorderLayout.CENTER);
    }

    public JPanel header() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        JLabel tanksLabel = new JLabel("Tanks");
        tanksLabel.setFont(Values.EXTRA_SUPER_LARGE_FONT);

        addNewTankButton = CustomComponents.button("Add");
        addNewTankButton.setFont(Values.EXTRA_SUPER_LARGE_FONT);

        JLabel money = new JLabel(user.getInventory().getMoney() + "$");
        money.setFont(Values.EXTRA_SUPER_LARGE_FONT);

        panel.add(tanksLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(addNewTankButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(money);
        panel.add(Box.createHorizontalStrut(10));

        return panel;
    }

    private JScrollPane generateTankScroll() {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        ArrayList<Tank> tanks = user.getInventory().getTanks();

        for (Tank tank : tanks) {
            JPanel cannonCard = new TankCard(tank, 200, 200, 120, 200, () -> {
                user.setCurrentTank(tank);
                JSONHelper.update("src/data/users.json",
                        "users",
                        "username",
                        user.getUsername(),
                        user.toJSON());
            });
            cannonCard.setMaximumSize(new Dimension(200, 405));
            cardPanel.add(cannonCard);
            cardPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        cardPanel.add(Box.createHorizontalGlue());

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBackground(new Color(0xD2B48C));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(750, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    public JButton getAddNewTankButton() {
        return addNewTankButton;
    }
}
