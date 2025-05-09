package core.panels.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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
    ArrayList<User> users;

    public GamePanel(Map map, User user, User[] users) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        this.users = new ArrayList<>(java.util.List.of(users));

        tank = user.getCurrentTank();

        Tank[] tanksArr = new Tank[users.length];
        for (int i = 0; i < users.length; i++){
            tanksArr[i] = users[i].getCurrentTank();
        }
        map.setTanksToDraw(tanksArr);
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

        generateMysteryBox(map);
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
        final boolean[] shouldClear = {false};

        CopyOnWriteArrayList<MysteryBox> activeMysteryBoxes = new CopyOnWriteArrayList<>();

        ActionListener addBoxes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cellSize = map.getCellSize();

                if (!shouldClear[0]) {
                    try {
                        if (activeMysteryBoxes.size() > 3) {
                            activeMysteryBoxes.clear();
                        } else {
                            activeMysteryBoxes.remove(activeMysteryBoxes.getFirst());
                        }
                    } catch (Exception ex) {

                    }
                }

                shouldClear[0] = !shouldClear[0];

                int row = 1;
                int col = 1;

                do {
                    row = (int) (Math.random() * layout.length);
                    col = (int) (Math.random() * layout[0].length);
                } while (layout[row][col] != Map.PATH);

                int x = col * cellSize + cellSize / 2;
                int y = row * cellSize + cellSize / 2;
                Point point = new Point(x, y);
                MysteryBox box;

                if (Math.random() >= 0.5) {
                    box = new BonusBox(point, new Dimension(cellSize, cellSize));
                } else {
                    box = new TrapBox(point, new Dimension(cellSize, cellSize));
                }

                activeMysteryBoxes.add(box);

                map.setMysteryBoxesToDraw(activeMysteryBoxes);
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
        tank.onKeyReleased(e, users);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
