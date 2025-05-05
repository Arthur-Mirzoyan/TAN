package entities.mysteryBox;

import entities.tank.Tank;
import utils.Point;

public abstract class MysteryBox {
    Point coordinate;

    public MysteryBox(Point point) {
        this.coordinate = new Point(point);
    }

    abstract void action(Tank tank);
}
