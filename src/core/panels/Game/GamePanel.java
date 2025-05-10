package core.panels.Game;

import entities.mysteryBox.BonusBox;
import entities.mysteryBox.MysteryBox;
import entities.mysteryBox.TrapBox;
import entities.tank.Tank;
import entities.tank.components.TankCard;
import entities.user.components.UserData;
import utils.CustomComponents;
import utils.Map;
import utils.Point;
import utils.Values;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code GamePanel} class is responsible for drawing game elements
 * and managing the display of the game during an active session.
 */
public class GamePanel extends JPanel implements KeyListener {
    private final CopyOnWriteArrayList<UserData> users;
    private final Tank userTank;
    private final JPanel mapPanel;

    private JButton exitButton;

    public GamePanel(UserData user, CopyOnWriteArrayList<UserData> users, Map map, Runnable action) {
        this.users = users;

        setOpaque(false);
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        userTank = user.getTank();
        userTank.setOwner(user);
        userTank.setMap(map);
        userTank.setPosition(map.getSpawnPoints().get(0));

        map.setTanksToDraw(getTanksToDraw());

        mapPanel = map.getPanel();

        add(generateGameInfo(), BorderLayout.WEST);
        add(generateUserInfo(user), BorderLayout.EAST);

        add(mapPanel, BorderLayout.CENTER);

        new Timer(16, e -> {
            action.run();
            userTank.updateTankPosition();
            mapPanel.repaint();
        }).start();

        generateMysteryBox(map);
    }

    public JPanel generateUserInfo(UserData user) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        panel.add(new TankCard(user.getTank(), 600, 700, 240, 400));

        return panel;
    }

    public JPanel generateGameInfo() {
        JPanel panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 25));


        JLabel players = new JLabel("Players");
        players.setFont(Values.EXTRA_SUPER_LARGE_FONT);
        players.setOpaque(false);
        players.setAlignmentX(Component.CENTER_ALIGNMENT);
        players.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (UserData user : users) {
            JLabel text = CustomComponents.label(user.getUsername());
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            text.setFont(Values.LARGE_FONT);
            descriptionPanel.add(Box.createVerticalStrut(3));
            descriptionPanel.add(text);
        }

        exitButton = CustomComponents.button("EXIT");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(Values.EXTRA_LARGE_FONT);


        panel.add(players);
        panel.add(Box.createVerticalStrut(10));
        panel.add(descriptionPanel);
        panel.add(Box.createVerticalGlue());
        panel.add(exitButton);
        panel.add(Box.createVerticalStrut(42));

        return panel;
    }

    private void generateMysteryBox(Map map) {
        byte[][] layout = map.getLayout();
        final boolean[] shouldClear = {false};

        CopyOnWriteArrayList<MysteryBox> activeMysteryBoxes = new CopyOnWriteArrayList<>();

        new Timer(10000, e -> {
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
        }).start();
    }

    private ArrayList<Tank> getTanksToDraw() {
        ArrayList<Tank> tanksToDraw = new ArrayList<>();

        for (UserData userData : users)
            tanksToDraw.add(userData.getTank());

        return tanksToDraw;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        userTank.onKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        userTank.onKeyReleased(e, users);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
