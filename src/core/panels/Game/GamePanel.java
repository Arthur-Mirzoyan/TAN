package core.panels.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import utils.Map;
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
