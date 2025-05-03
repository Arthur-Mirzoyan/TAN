package core;

import javax.swing.*;
import java.awt.*;

import core.panels.Game.Game;
import core.panels.Lobby.Lobby;
import core.panels.LogIn.LogIn;
import core.panels.Menu.Menu;
import core.panels.SignUp.SignUp;
import utils.PanelListener;
import utils.Panels;

public class MainWindow implements PanelListener {
    private JFrame window;
    private JPanel background;

    public MainWindow() {
        initialize();
        addBackground();
        switchPanel((new Game(this)).getPanel());
    }

    public void initialize() {
        window = new JFrame();

        window.setTitle("TAN - Terminate Advance Neutralise");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 450);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void show() {
        window.setVisible(true);
    }

    public void switchPanel(JPanel panel) {
        background.removeAll();
        background.add(panel);
        background.revalidate();
        background.repaint();
    }

    @Override
    public void goTo(Panels panel) {
        switch (panel) {
            case Panels.LOGIN:
                switchPanel((new LogIn(this)).getPanel());
                break;
            case Panels.SIGNUP:
                switchPanel(new SignUp(this).getPanel());
                break;
            case Panels.MENU:
                switchPanel(new Menu(this).getPanel());
                break;
        }
    }

    private void addBackground() {
        background = new JPanel() {
            Image img = new ImageIcon("src/assets/img/background.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Calculate scaling factors
                double imgWidth = img.getWidth(null);
                double imgHeight = img.getHeight(null);

                double scaleX = panelWidth / imgWidth;
                double scaleY = panelHeight / imgHeight;

                // Use the larger scale factor to ensure full coverage
                double scale = Math.max(scaleX, scaleY);

                // Calculate new dimensions
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);

                // Calculate position to center the image
                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                // Draw the scaled image
                g.drawImage(img, x, y, scaledWidth, scaledHeight, null);
            }
        };

        background.setLayout(new BorderLayout());

        window.setContentPane(background);
    }
}
