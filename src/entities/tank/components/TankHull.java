package entities.tank.components;

import org.json.JSONObject;
import utils.CustomComponents;
import utils.ImageDrawer;
import utils.JSONHelper;
import utils.Values;

import javax.swing.*;
import java.awt.*;

public class TankHull implements Cloneable {
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

    public TankHull(JSONObject json) {
        this.id = JSONHelper.getValue(json, "id", 1);
        this.name = JSONHelper.getValue(json, "name", "");
        this.price = JSONHelper.getValue(json, "price", 1);
        this.speed = JSONHelper.getValue(json, "speed", 1);
        this.armorStrength = JSONHelper.getValue(json, "armorStrength", 1);
        this.level = Level.PRIVATE; // TODO: should take from json
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

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("id", id);
        json.put("name", name);
        json.put("price", price);
        json.put("speed", speed);
        json.put("armorStrength", armorStrength);

        return json;
    }

    @Override
    public TankHull clone() throws CloneNotSupportedException {
        return (TankHull) super.clone();
    }
}
