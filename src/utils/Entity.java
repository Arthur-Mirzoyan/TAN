package utils;

public abstract class Entity {
    private Point position;
    private Dimension dimension;
    private Collider collider;

    public Entity(Point position, Dimension dimension) {
        this.position = position;
        this.dimension = dimension;
        this.collider = new Collider(position, dimension);
    }

    public Point getPosition() {
        return new Point(position);
    }

    protected void setPosition(Point position) {
        this.position = position;
    }

    public Dimension getDimension() {
        return dimension;
    }

    protected abstract void draw();
}
