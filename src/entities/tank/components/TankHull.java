package entities.tank.components;

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
