package core.panels.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import entities.mysteryBox.BonusBox;
import entities.mysteryBox.MysteryBox;
import entities.mysteryBox.TrapBox;
import utils.Map;
import utils.Point;
import entities.tank.Tank;
import entities.user.User;
import utils.CustomComponents;

public class GamePanel extends JPanel implements KeyListener {
    JPanel mapPanel;
    Tank tank;

    public GamePanel(Map map, User user, User[] users) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        tank = user.getCurrentTank();
        map.setTanksToDraw(new Tank[]{user.getCurrentTank()});
        mapPanel = map.getPanel();

        add(generateGameInfo(users), BorderLayout.WEST);
        add(generateUserInfo(user), BorderLayout.EAST);
        add(mapPanel, BorderLayout.CENTER);

        Timer gameLoop = new Timer(16, e -> {
            updateGame();
            tank.updateTankPosition();
            mapPanel.repaint();
        });
        gameLoop.start();
    }

    public JPanel generateUserInfo(User user) {
        JPanel panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel text1 = CustomComponents.label(user.getUsername());

        panel.add(text1);

        panel.setBorder(BorderFactory.createLineBorder(Color.RED, 50));

        return panel;
    }

    public JPanel generateGameInfo(User[] users) {
        JPanel panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (User user : users) {
            JLabel text = CustomComponents.label(user.getUsername());
            panel.add(text);
        }

        panel.setBorder(BorderFactory.createLineBorder(Color.RED, 50));

        return panel;
    }

    private void updateGame() {
        // TODO: draw opponents tanks and bullets
    }

    private void generateMysteryBox(Map map) {
        byte[][] layout = map.getLayout();
        int cellSize = map.getCellSize();
        final boolean[] shouldClear = {false};

        ArrayList<MysteryBox> activeMysteryBoxes = new ArrayList<>();

        ActionListener addBoxes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shouldClear[0]) activeMysteryBoxes.clear();

                shouldClear[0] = !shouldClear[0];

                int row = 1;
                int col = 1;

                do {
                    row = (int) Math.random() * layout.length;
                    col = (int) Math.random() * layout[0].length;
                } while (layout[row][col] != Map.WALL);

                int x = col * cellSize + cellSize / 2;
                int y = row * cellSize + cellSize / 2;
                Point point = new Point(x, y);
                MysteryBox box;

                if (Math.random() >= 0.5) {
                    box = new BonusBox(point);
                    map.setLayout(row, col, (byte) BonusBox.BONUS_INDEX);
                } else {
                    box = new TrapBox(point);
                    map.setLayout(row, col, (byte) TrapBox.TRAP_INDEX);
                }

                activeMysteryBoxes.add(box);
            }
        };

        new Timer(10000, addBoxes).start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        tank.onKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        tank.onKeyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
