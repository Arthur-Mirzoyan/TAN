package utils;

import entities.mysteryBox.MysteryBox;
import entities.tank.Tank;
import entities.tank.components.Bullet;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the game map composed of a grid layout, tanks, bullets, and mystery boxes.
 * Handles rendering and dimension management based on layout configuration.
 */
public class Map {
    public static final byte PATH = 0;
    public static final byte WALL = 1;

    private final JPanel panel;
    private final byte[][] layout;
    private final ArrayList<Point> spawnPoints;

    private Image mysteryBoxImage;
    private Dimension dimension;
    private ArrayList<Tank> tanksToDraw;
    private CopyOnWriteArrayList<MysteryBox> mysteryBoxesToDraw = new CopyOnWriteArrayList<>();

    private int cellSize;

    /**
     * Constructs a new map using a byte matrix layout and a list of spawn points.
     *
     * @param layout      2D byte array indicating path and wall tiles
     * @param spawnPoints list of spawn locations for tanks
     */
    public Map(byte[][] layout, ArrayList<Point> spawnPoints) {
        this.layout = layout;
        this.dimension = new Dimension();
        this.spawnPoints = spawnPoints;

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int rows = layout.length;
                int cols = layout[0].length;
                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
                setSize(cellSize * cols, cellSize * rows);

                updateDimension(cellSize);

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        int x = col * cellSize;
                        int y = row * cellSize;

                        if (layout[row][col] == WALL) g.setColor(Values.PRIMARY_COLOR);
                        else if (layout[row][col] == PATH) g.setColor(Values.SECONDARY_COLOR);

                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }

                drawMysteryBoxes(g);
                drawTanks(g);
            }
        };
    }

    /**
     * Constructs a map using JSON data describing layout and spawn points.
     *
     * @param json JSONObject with keys "layout" and "spawnPoints"
     */
    public Map(JSONObject json) {
        JSONArray layout = json.getJSONArray("layout");
        JSONArray points = json.getJSONArray("spawnPoints");

        byte[][] byteLayout = new byte[layout.length()][];
        ArrayList<Point> spawnPoints = new ArrayList<>();

        for (int i = 0; i < layout.length(); i++) {
            JSONArray row = layout.getJSONArray(i);
            byte[] byteRow = new byte[row.length()];

            for (int j = 0; j < row.length(); j++) byteRow[j] = (byte) row.getInt(j);

            byteLayout[i] = byteRow;
        }

        for (int i = 0; i < points.length(); i++)
            spawnPoints.add(new Point((JSONObject) points.get(i)));

        this(byteLayout, spawnPoints);
    }

    public void setTanksToDraw(ArrayList<Tank> tanks) {
        this.tanksToDraw = tanks;
    }

    public void setMysteryBoxesToDraw(CopyOnWriteArrayList<MysteryBox> mysteryBoxesToDraw) {
        this.mysteryBoxesToDraw = mysteryBoxesToDraw;
    }

    private void updateDimension(int cellSize) {
        if (this.cellSize != cellSize)
            mysteryBoxImage = MysteryBox.image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);

        this.cellSize = cellSize;

        double newWidth = layout[0].length * cellSize;
        double newHeight = layout.length * cellSize;
        double currentWidth = dimension.getWidth();
        double currentHeight = dimension.getHeight();

        if (currentWidth != newWidth) dimension.setSize(newWidth, currentHeight);
        if (currentHeight != newHeight) dimension.setSize(currentWidth, newHeight);
    }

    public int getCellSize() {
        return cellSize;
    }

    public double getWidth() {
        return dimension.getWidth();
    }

    public double getHeight() {
        return dimension.getHeight();
    }

    public byte[][] getLayout() {
        byte[][] res = new byte[layout.length][];

        for (int i = 0; i < layout.length; i++) {
            res[i] = new byte[layout[i].length];
            for (int j = 0; j < layout[i].length; j++) res[i][j] = layout[i][j];
        }

        return res;
    }

    public ArrayList<Point> getSpawnPoints() {
        return spawnPoints;
    }

    public JPanel getPanel() {
        return panel;
    }

    public CopyOnWriteArrayList<MysteryBox> getMysteryBoxesToDraw() {
        return mysteryBoxesToDraw;
    }

    /**
     * Draws all tanks and their bullets on the panel.
     */
    private void drawTanks(Graphics g) {
        if (tanksToDraw == null) return;

        // Setting bullet color
        g.setColor(Bullet.COLOR);

        for (Tank tank : tanksToDraw) {
            if (tank != null) {
                Point position = tank.getPosition();
                BufferedImage rotatedImage = tank.getRotatedImage();
                Image image = rotatedImage == null ? tank.getImage() : rotatedImage;

                int imageWidthHalf = image.getWidth(null) / 2;
                int imageHeightHalf = image.getHeight(null) / 2;

                g.drawImage(image, position.getX() - imageWidthHalf, position.getY() - imageHeightHalf, panel);

                for (Bullet bullet : tank.getCannon().getBullets()) {
                    if (bullet.isFiring()) {
                        Point pos = bullet.getCurrentPosition();
                        g.fillOval(pos.getX(), pos.getY(), Bullet.SIZE, Bullet.SIZE);
                    }
                }
            }
        }
    }

    /**
     * Draws all visible mystery boxes on the map.
     */
    private void drawMysteryBoxes(Graphics g) {
        if (mysteryBoxesToDraw != null) {
            int imageWidthHalf = mysteryBoxImage.getWidth(null) / 2;
            int imageHeightHalf = mysteryBoxImage.getHeight(null) / 2;

            for (MysteryBox box : mysteryBoxesToDraw) {
                Point position = box.getPosition();
                g.drawImage(mysteryBoxImage, position.getX() - imageWidthHalf, position.getY() - imageHeightHalf, panel);
            }
        }
    }
}
