package core.panels.Menu;

import utils.CustomComponents;
import utils.Values;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton playButton;
    private JButton inventoryButton;
    private JButton shopButton;
    private JButton volumeConfiguratorButton;
    private ImageIcon volumeIcon;
    private ImageIcon muteIcon;

    public MenuPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
        add(generateButtonBox(), BorderLayout.CENTER);
        add(generateStatisticsBox(), BorderLayout.SOUTH);
        add(generateVolumeConfiguratorBox(), BorderLayout.NORTH);
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getInventoryButton() {
        return inventoryButton;
    }

    public JButton getShopButton() {
        return shopButton;
    }

    public JButton getVolumeConfiguratorButton() {
        return volumeConfiguratorButton;
    }

    public ImageIcon getVolumeIcon() {
        return volumeIcon;
    }

    public ImageIcon getMuteIcon() {
        return muteIcon;
    }

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

    private JPanel generateStatisticsBox() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel gamesPlayedLabel = new JLabel("Games Played ");
        gamesPlayedLabel.setFont(Values.EXTRA_LARGE_FONT);
        gamesPlayedLabel.setForeground(Values.PRIMARY_COLOR);
        gamesPlayedLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel gamesWonLabel = new JLabel("Games Won ");
        gamesWonLabel.setFont(Values.EXTRA_LARGE_FONT);
        gamesWonLabel.setForeground(Values.PRIMARY_COLOR);
        gamesWonLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel gamesLostLabel = new JLabel("Games Lost ");
        gamesLostLabel.setFont(Values.EXTRA_LARGE_FONT);
        gamesLostLabel.setForeground(Values.PRIMARY_COLOR);
        gamesLostLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel gamesPlayedCount = new JLabel(String.valueOf(0));
        gamesPlayedCount.setFont(Values.EXTRA_LARGE_FONT);
        gamesPlayedCount.setForeground(Values.PRIMARY_COLOR);

        JLabel gamesWonCount = new JLabel(String.valueOf(0));
        gamesWonCount.setFont(Values.EXTRA_LARGE_FONT);
        gamesWonCount.setForeground(Values.PRIMARY_COLOR);

        JLabel gamesLostCount = new JLabel(String.valueOf(0));
        gamesLostCount.setFont(Values.EXTRA_LARGE_FONT);
        gamesLostCount.setForeground(Values.PRIMARY_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(gamesPlayedLabel, gbc);

        gbc.gridx = 1;
        panel1.add(gamesPlayedCount, gbc);

        gbc.gridy = 1;
        panel1.add(gamesWonCount, gbc);

        gbc.gridx = 0;
        panel1.add(gamesWonLabel, gbc);

        gbc.gridy = 2;
        panel1.add(gamesLostLabel, gbc);

        gbc.gridx = 1;
        panel1.add(gamesLostCount, gbc);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 80));
        panel.add(panel1, BorderLayout.WEST);
        return panel;
    }

    private JPanel generateVolumeConfiguratorBox() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        volumeIcon = new ImageIcon(new ImageIcon("src/assets/img/volume_button.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        muteIcon = new ImageIcon(new ImageIcon("src/assets/img/mute_button.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        volumeConfiguratorButton = new JButton(volumeIcon);
        volumeConfiguratorButton.setOpaque(false);
        volumeConfiguratorButton.setContentAreaFilled(false);
        volumeConfiguratorButton.setBorderPainted(false);
        volumeConfiguratorButton.setFocusPainted(false);
        panel.add(volumeConfiguratorButton, BorderLayout.EAST);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
        return panel;
    }
}
