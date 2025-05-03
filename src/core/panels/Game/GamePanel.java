package core.panels.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import entities.Map;
import entities.tank.Tank;
import entities.user.User;
import utils.CustomComponents;
import utils.ImageDrawer;

public class GamePanel extends JPanel {
    JPanel panel;

    public GamePanel(Map map, User user, User[] users) {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel mapPanel = generateMap(map);
        JPanel userInfoPanel = generateUserInfo(user);
        JPanel gameInfoPanel = generateGameInfo(users);

        add(gameInfoPanel, BorderLayout.WEST);
        add(mapPanel, BorderLayout.CENTER);
        add(userInfoPanel, BorderLayout.EAST);
    }

    public JPanel generateMap(Map map) {
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                byte[][] layout = map.getLayout();
                int rows = layout.length;
                int cols = layout[0].length;
                int cellSize = Math.min(getWidth(), getHeight()) / Math.max(rows, cols);

                this.setSize(cellSize * cols, cellSize * rows);

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        int x = col * cellSize;
                        int y = row * cellSize;

                        if (layout[row][col] == 1) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }

                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }
        };

//        mapPanel.setOpaque(false);
//        mapPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 50));

        return mapPanel;
    }

    public JPanel generateUserInfo(User user) {
        JPanel panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel text1 = CustomComponents.label("Text1");

        panel.add(text1);

        panel.setBorder(BorderFactory.createLineBorder(Color.RED, 50));

        return panel;
    }

    public JPanel generateGameInfo(User[] user) {
        JPanel panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel text1 = CustomComponents.label("User1: 500");
        JLabel text2 = CustomComponents.label("User2: 250");
        JLabel text3 = CustomComponents.label("User3: 400");

        panel.add(text1);
        panel.add(text2);
        panel.add(text3);

        panel.setBorder(BorderFactory.createLineBorder(Color.RED, 50));

        return panel;
    }

    private void updateRotatedTankImage(Tank tank) {
        Image tankImage = tank.getImage();

        if (tankImage == null) return;

        int tankImageWidth = tankImage.getWidth(null);
        int tankImageHeight = tankImage.getWidth(null);
        int tankAngle = tank.getAngle();

        double diagonal = Math.sqrt(tankImageWidth * tankImageWidth + tankImageHeight * tankImageHeight);
        tank.setRotatedImage(new BufferedImage((int) diagonal, (int) diagonal, BufferedImage.TYPE_INT_ARGB));

        Graphics2D rg2d = tank.getRotatedImage().createGraphics();

        rg2d.translate(diagonal / 2, diagonal / 2);
        rg2d.rotate(Math.toRadians(-tankAngle));
        rg2d.drawImage(tankImage, -tankImageWidth / 2, -tankImageHeight / 2, null);
        rg2d.dispose();

        tank.setLastAngle(-tankAngle);
    }

    private Image loadTankImage(String tankId) {
        String path = "assets/img/tanks/" + tankId + ".png";

        ImageDrawer imageDrawer = new ImageDrawer(path);
        Image tankImage = imageDrawer.getScaledImage(0.25);

        if (tankImage != null) {
            MediaTracker tracker = new MediaTracker(null); // maybe null is wrong
            tracker.addImage(tankImage, 0);
            try {
                tracker.waitForAll();
//                updateRotatedTankImage(); // move to tank initialization
            } catch (InterruptedException e) {
                System.err.println("Image loading interrupted");
            }
        }

        return tankImage;
    }
}
