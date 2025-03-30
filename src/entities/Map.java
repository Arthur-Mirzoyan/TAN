package entities;

public class Map {
    private final int width;
    private final int height;
    private final byte[][] layout;

    public Map(int width, int height, byte[][] layout) {
        this.width = width;
        this.height = height;
        this.layout = layout;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[][] getLayout() {
        return layout;
    }

    public void draw() {
    }
}
