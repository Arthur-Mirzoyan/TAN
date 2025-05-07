package entities.tank.components;

import utils.CustomComponents;
import utils.ImageDrawer;
import utils.Values;

import javax.swing.*;
import java.awt.*;

public class TankHull {
    private final int id;
    private final String name;
    private final int price;

    private int armorStrength;
    private int speed;

    private int health;
    private Level level;

    public TankHull(int id, String name, int price, int speed, int armorStrength) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.armorStrength = armorStrength;

        this.health = 100;
        this.level = Level.PRIVATE;
    }

    public TankHull(TankHull other) {
        this.id = other.id;
        this.name = other.name;
        this.price = other.price;
        this.speed = other.speed;
        this.armorStrength = other.armorStrength;
        this.health = other.getHealth();
        this.level = other.getLevel();
    }

    public JPanel generateTankHullCard(TankHull tankHull) {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel nameLabel = CustomComponents.label(tankHull.getName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Image image = new ImageDrawer("assets/img/tankHull/hull" + tankHull.getId() + ".png").getScaledImage(50, 100);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));

        JLabel speed = new JLabel("Speed: " + tankHull.getSpeed());
        speed.setAlignmentX(Component.LEFT_ALIGNMENT);
        speed.setFont(Values.SMALL_FONT);
        descriptionPanel.add(speed);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel armor = new JLabel("Armor: " + tankHull.getArmorStrength());
        armor.setAlignmentX(Component.LEFT_ALIGNMENT);
        armor.setFont(Values.SMALL_FONT);
        descriptionPanel.add(armor);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel health = new JLabel("Health: " + tankHull.getHealth());
        health.setAlignmentX(Component.LEFT_ALIGNMENT);
        health.setFont(Values.SMALL_FONT);
        descriptionPanel.add(health);
        descriptionPanel.add(Box.createVerticalStrut(3));

        JLabel level = new JLabel("Level: " + tankHull.getLevel());
        level.setAlignmentX(Component.LEFT_ALIGNMENT);
        level.setFont(Values.SMALL_FONT);
        descriptionPanel.add(level);

        JButton buyButton = new JButton("Buy");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(nameLabel);
        cardPanel.add(Box.createVerticalStrut(10));

        cardPanel.add(imageLabel);
        cardPanel.add(Box.createVerticalStrut(10));

        cardPanel.add(descriptionPanel);
        cardPanel.add(Box.createVerticalStrut(10));

        cardPanel.add(buyButton);

        return cardPanel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getArmorStrength() {
        return armorStrength;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public Level getLevel() {
        return level;
    }

    public void setArmorStrength(int armorStrength) {
        this.armorStrength = armorStrength;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void upgrade() {
    }

    public void move() {
    }

    public void draw() {
    }
}
