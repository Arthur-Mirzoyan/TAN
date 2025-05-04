package utils;

public abstract class Entity extends Collider{
    public Entity(Point position, Dimension dimension) {
        super(position, dimension);
    }

    public Entity(Point position) {
        this(position, new Dimension(0, 0));
    }

    protected Entity(Entity other) {
        super(other);
    }

    protected void setPosition(Point position) {
        this.position = position;
    }

    protected Point getPosition() {
        return new Point(position);
    }
}
