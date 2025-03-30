package entities.tank.components;

public class TankHull {
    private final String id;
    private final String name;
    private final int price;

    private int armorStrength;
    private int health;
    private int speed;
    private Level level;

    public TankHull(String id, String name, int price, int health, int speed, int armorStrength) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.health = health;
        this.speed = speed;
        this.armorStrength = armorStrength;
        this.level = Level.PRIVATE;
    }

    public String getId() {
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
