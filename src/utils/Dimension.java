package utils;

public class Dimension {
    private double width;
    private double height;

    public Dimension(double width, double height) {
        this.width = Math.abs(width);
        this.height = Math.abs(height);
    }

    public Dimension(Dimension d) {
        this.width = d.width;
        this.height = d.height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
