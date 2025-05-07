package utils;

import entities.mysteryBox.*;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import entities.tank.Tank;
import entities.tank.components.Bullet;

public class Map {
    public static final byte PATH = 0;
    public static final byte WALL = 1;

    private final JPanel panel;
    private final byte[][] layout;

    private Dimension dimension;
    private Tank[] tanksToDraw;
    private int cellSize;

    public Map(byte[][] layout) {
        this.layout = layout;
        this.dimension = new Dimension(0, 0);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int rows = layout.length;
                int cols = layout[0].length;
                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
                setSize(cellSize * cols, cellSize * rows);

                updateDimension(cellSize);

                Image mysteryBoxImage = MysteryBox.image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                int mysteryBoxWidthHalf = mysteryBoxImage.getWidth(null) / 2;
                int mysteryBoxHeightHalf = mysteryBoxImage.getHeight(null) / 2;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        int x = col * cellSize;
                        int y = row * cellSize;

                        if (layout[row][col] == WALL)
                            g.setColor(Color.BLACK);
                        else if (layout[row][col] == PATH)
                            g.setColor(Color.WHITE);

                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        if (layout[row][col] == BonusBox.BONUS_INDEX || layout[row][col] == TrapBox.TRAP_INDEX) {
                            Point position = new Point(col * cellSize, row * cellSize);
                            g.drawImage(mysteryBoxImage, position.getX(), position.getY(), panel);
                        }
                    }
                }

                drawTanks(g);
            }
        };
    }

    public Map(JSONObject json) {
        JSONArray layout = json.getJSONArray("layout");

        byte[][] byteLayout = new byte[layout.length()][];

        for (int i = 0; i < layout.length(); i++) {
            JSONArray row = layout.getJSONArray(i);
            byte[] byteRow = new byte[row.length()];

            for (int j = 0; j < row.length(); j++) byteRow[j] = (byte) row.getInt(j);

            byteLayout[i] = byteRow;
        }

        this(byteLayout);
    }

    public void setLayout(int row, int col, byte value) {
        if (row >= layout.length || col >= layout[0].length) return;
        if (layout[row][col] != Map.WALL) layout[row][col] = value;
    }

    public void setTanksToDraw(Tank[] tanks) {
        this.tanksToDraw = tanks;
    }

    private void updateDimension(int cellSize) {
        this.cellSize = cellSize;

        double newWidth = layout[0].length * cellSize;
        double newHeight = layout.length * cellSize;

        if (dimension.getWidth() != newWidth) dimension.setWidth(newWidth);
        if (dimension.getHeight() != newHeight) dimension.setHeight(newHeight);
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

    public JPanel getPanel() {
        return panel;
    }

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
}
