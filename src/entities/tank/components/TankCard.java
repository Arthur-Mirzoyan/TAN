package entities.tank.components;

import entities.tank.Tank;
import utils.CustomComponents;
import utils.ImageDrawer;
import utils.Values;

import javax.swing.*;
import java.awt.*;

public class TankCard extends JPanel {
    private JButton button;

    public TankCard(Tank tank, int panelWidth, int panelHeight, int imageWidth, int imageHeight, Runnable action) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(0xE6D8B5));
        setPreferredSize(new java.awt.Dimension(panelWidth, panelHeight));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nameLabel = CustomComponents.label("Custom Tank");
        nameLabel.setOpaque(false);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Image image = ImageDrawer.rotateImage(new ImageDrawer("assets/img/tanks/tank_" + tank.getHull().getId() + "_" + tank.getCannon().getId() + ".png").getImage(), -90).getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel speed = new JLabel("Speed: " + tank.getHull().getSpeed());
        speed.setAlignmentX(Component.CENTER_ALIGNMENT);
        speed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(speed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel armor = new JLabel("Armor: " + tank.getHull().getArmorStrength());
        armor.setAlignmentX(Component.CENTER_ALIGNMENT);
        armor.setFont(Values.SMALL_FONT);
        descriptionPanel.add(armor);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel bulletSpeed = new JLabel("Bullet Speed: " + tank.getCannon().getBulletSpeed());
        bulletSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
        bulletSpeed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(bulletSpeed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel reloadSpeed = new JLabel("Reload Speed: " + tank.getCannon().getReloadSpeed());
        reloadSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
        reloadSpeed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(reloadSpeed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel ammo = new JLabel("Ammo: " + tank.getCannon().getAmmo());
        ammo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ammo.setFont(Values.SMALL_FONT);
        descriptionPanel.add(ammo);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel firingRange = new JLabel("Firing Range: " + tank.getCannon().getFiringRange());
        firingRange.setAlignmentX(Component.CENTER_ALIGNMENT);
        firingRange.setFont(Values.SMALL_FONT);
        descriptionPanel.add(firingRange);

        button = CustomComponents.button("Select");
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(e -> action.run());

        add(nameLabel);
        add(Box.createVerticalStrut(10));

        add(imageLabel);
        add(Box.createVerticalStrut(10));

        add(descriptionPanel);
        add(Box.createVerticalStrut(10));

        add(button);
    }

    public TankCard(TankHull hull, String buttonLabel, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(0xE6D8B5));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nameLabel = CustomComponents.label(hull.getName());
        nameLabel.setOpaque(false);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Image image = new ImageDrawer("assets/img/tankHull/hull" + hull.getId() + ".png").getScaledImage(imageWidth, imageHeight);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel speed = new JLabel("Speed: " + hull.getSpeed());
        speed.setAlignmentX(Component.CENTER_ALIGNMENT);
        speed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(Box.createVerticalStrut(16));
        descriptionPanel.add(speed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel armor = new JLabel("Armor: " + hull.getArmorStrength());
        armor.setAlignmentX(Component.CENTER_ALIGNMENT);
        armor.setFont(Values.SMALL_FONT);
        descriptionPanel.add(armor);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel price = new JLabel("Price: " + hull.getPrice());
        price.setAlignmentX(Component.CENTER_ALIGNMENT);
        price.setFont(Values.SMALL_FONT);
        descriptionPanel.add(price);
        descriptionPanel.add(Box.createVerticalStrut(16));

        button = CustomComponents.button(buttonLabel);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(nameLabel);
        add(Box.createVerticalStrut(10));

        add(imageLabel);
        add(Box.createVerticalStrut(10));

        add(descriptionPanel);
        add(Box.createVerticalStrut(10));

        add(button);
    }

    public TankCard(TankCannon cannon, String buttonLabel, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(0xE6D8B5));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nameLabel = CustomComponents.label(cannon.getName());
        nameLabel.setOpaque(false);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Image image = new ImageDrawer("assets/img/tankCannon/cannon" + cannon.getId() + ".png").getScaledImage(imageWidth, imageHeight);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setMaximumSize(new Dimension(150, 200));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bulletSpeed = new JLabel("Bullet Speed: " + cannon.getBulletSpeed());
        bulletSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
        bulletSpeed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(bulletSpeed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel reloadSpeed = new JLabel("Reload Speed: " + cannon.getReloadSpeed());
        reloadSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
        reloadSpeed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(reloadSpeed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel ammo = new JLabel("Ammo: " + cannon.getAmmo());
        ammo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ammo.setFont(Values.SMALL_FONT);
        descriptionPanel.add(ammo);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel firingRange = new JLabel("Firing Range: " + cannon.getFiringRange());
        firingRange.setAlignmentX(Component.CENTER_ALIGNMENT);
        firingRange.setFont(Values.SMALL_FONT);
        descriptionPanel.add(firingRange);

        JLabel price = new JLabel("Price: " + cannon.getPrice());
        price.setAlignmentX(Component.CENTER_ALIGNMENT);
        price.setFont(Values.SMALL_FONT);
        descriptionPanel.add(price);

        button = CustomComponents.button(buttonLabel);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(nameLabel);
        add(Box.createVerticalStrut(10));

        add(imageLabel);
        add(Box.createVerticalStrut(10));

        add(descriptionPanel);
        add(Box.createVerticalStrut(10));

        add(button);
    }

    public JButton getButton() {
        return button;
    }

    public void setButtonLabel(String newLabel) {
        button.setText(newLabel);
    }
}
