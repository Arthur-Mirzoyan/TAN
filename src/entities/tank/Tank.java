package entities.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

import entities.tank.components.TankCannon;
import entities.tank.components.TankHull;
import utils.Entity;
import utils.Dimension;
import utils.Point;

public class Tank extends Entity {
    private TankHull hull;
    private TankCannon cannon;

    private Image image;
    private BufferedImage rotatedImage;
    private int angle = 0;
    private int lastAngle = -1;

    public Tank(Image image, Point position, TankHull hull, TankCannon cannon) {
        super(position, new Dimension(image.getWidth(null), image.getHeight(null)));

        this.image = image;
        this.hull = hull;
        this.cannon = cannon;
    }

    public void setRotatedImage(BufferedImage image) {
        this.rotatedImage = image;
    }

    public void setLastAngle(int angle) {
        this.lastAngle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public Image getImage() {
        return image;
    }

    public BufferedImage getRotatedImage() {
        return rotatedImage;
    }

    public TankHull getHull() {
        return hull;
    }

    public TankCannon getCannon() {
        return cannon;
    }

    @Override
    protected void draw() {

    }
}
