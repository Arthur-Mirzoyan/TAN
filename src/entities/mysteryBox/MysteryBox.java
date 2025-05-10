package entities.mysteryBox;

import entities.tank.Tank;
import utils.Collider;
import utils.ImageDrawer;
import utils.Point;

import java.awt.*;

/**
 * The {@code MysteryBox} class is an abstract base for all interactive mystery items
 * on the game map. These boxes trigger random effects when a tank collides with them.
 */
public abstract class MysteryBox extends Collider {

    /**
     * The visual image used to render a mystery box.
     */
    public static final Image image = new ImageDrawer("assets/img/mysteryBox.jpg").getImage();

    /**
     * Constructs a {@code MysteryBox} at a given location with a given size.
     *
     * @param point     the position of the box
     * @param dimension the dimensions of the box
     */
    public MysteryBox(Point point, Dimension dimension) {
        super(point, dimension);
    }

    /**
     * Returns the position of the mystery box.
     *
     * @return the {@code Point} representing the location
     */
    public Point getPosition() {
        return super.getPosition();
    }

    /**
     * Abstract method triggered when a {@link Tank} collides with the box.
     * Subclasses must implement specific effect behavior.
     *
     * @param tank the tank affected by the box
     */
    public abstract void action(Tank tank);
}

