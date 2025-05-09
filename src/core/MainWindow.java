package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import core.panels.Game.Game;
import core.panels.Lobby.Lobby;
import core.panels.LogIn.LogIn;
import core.panels.Menu.Menu;
import core.panels.SignUp.SignUp;
import core.panels.Inventory.Inventory;
import core.panels.Shop.Shop;
import entities.user.User;
import utils.JSONHelper;
import utils.PanelListener;
import utils.Panels;

public class MainWindow implements PanelListener {
    private JFrame window;
    private JPanel background;

    private ArrayList<User> users;

    public MainWindow() {
        initialize();
        addBackground();
        switchPanel((new LogIn(this, users)).getPanel());
    }

    public void initialize() {
        window = new JFrame();

        window.setTitle("TAN - Terminate Advance Neutralise");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 450);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        getUsers();
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

    private void getUsers() {
        users = JSONHelper.parse("src/data/users.json", "users", json -> new User(json));
    }

    @Override
    public void goTo(Panels panel) {
        switch (panel) {
            case Panels.LOGIN:
                switchPanel((new LogIn(this, users)).getPanel());
                break;
            case Panels.SIGNUP:
                switchPanel(new SignUp(this, users).getPanel());
                break;
        }
    }

    @Override
    public void goTo(Panels panel, User user) {
        switch (panel) {
            case Panels.MENU:
                switchPanel(new Menu(this, user).getPanel());
                break;
            case Panels.LOBBY:
                switchPanel(new Lobby(this, user).getPanel());
                break;
            case Panels.INVENTORY:
                switchPanel(new Inventory(this, user).getPanel());
                break;
            case Panels.SHOP:
                switchPanel(new Shop(this, user).getPanel());
                break;
            case Panels.GAME:
                switchPanel(new Game(this, user).getPanel());
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
