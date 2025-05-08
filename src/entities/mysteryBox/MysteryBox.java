package entities.mysteryBox;

import entities.tank.Tank;
import utils.ImageDrawer;
import utils.Point;

import java.awt.*;

public abstract class MysteryBox {
    public static final Image image = new ImageDrawer("assets/img/mysteryBox.jpg").getImage();

    private Point position;

    public MysteryBox(Point point) {
        this.position = new Point(point);
    }

    public Point getPosition() {
        return new Point(position);
    }

    abstract void action(Tank tank);
}
