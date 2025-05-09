package entities.mysteryBox;

import entities.tank.Tank;
import utils.Collider;
import utils.ImageDrawer;
import utils.Point;

import java.awt.*;

public abstract class MysteryBox extends Collider {
    public static final Image image = new ImageDrawer("assets/img/mysteryBox.jpg").getImage();

    public MysteryBox(Point point, Dimension dimension) {
        super(point, dimension);
    }

    public Point getPosition() {
        return super.getPosition();
    }

    public abstract void action(Tank tank);
}

