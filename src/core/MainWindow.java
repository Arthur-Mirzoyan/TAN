package core;

import core.panels.Game.Game;
import core.panels.Inventory.Inventory;
import core.panels.Inventory.TankAdding;
import core.panels.Lobby.Lobby;
import core.panels.LogIn.LogIn;
import core.panels.Menu.Menu;
import core.panels.Shop.Shop;
import core.panels.SignUp.SignUp;
import entities.user.User;
import entities.user.components.UserData;
import network.Client;
import network.Server;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code MainWindow} class represents the primary GUI window for the application.
 * It sets up and manages the various components of the interface.
 */
public class MainWindow implements PanelListener {
    private JFrame window;
    private JPanel background;
    private JButton backButton;

    private ArrayList<User> users;

    /**
     * Constructs the main window and initializes all GUI components.
     */
    public MainWindow() {
        initialize();
        addBackground();
        switchPanel((new LogIn(this, users)).getPanel());
    }

    public void initialize() {
        window = new JFrame();

        window.setTitle("TAN - Terminate Advance Neutralise");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.setIconImage(new ImageDrawer("assets/img/logo.jpg").getImage());
        getUsers();
    }

    public JMenuBar createMenuBar(String panelName, Runnable action) {
        JMenuBar menuBar = new JMenuBar();

        menuBar.setLayout(new BorderLayout());
        menuBar.setOpaque(false);
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        if (action != null) {
            backButton = CustomComponents.button("Back");
            backButton.addActionListener(e -> action.run());
            menuBar.add(backButton, BorderLayout.WEST);
        }

        JLabel label = new JLabel(panelName);
        label.setFont(Values.EXTRA_LARGE_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(label, BorderLayout.CENTER);

        return menuBar;
    }

    /**
     * Displays the window by making it visible.
     */
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
                window.setJMenuBar(createMenuBar("Menu", null));
                break;
            case Panels.LOBBY:
                switchPanel(new Lobby(this, user).getPanel());
                window.setJMenuBar(createMenuBar("Lobby", () -> goTo(Panels.MENU, user)));
                break;
            case Panels.INVENTORY:
                switchPanel(new Inventory(this, user).getPanel());
                window.setJMenuBar(createMenuBar("Inventory", () -> goTo(Panels.MENU, user)));
                break;
            case Panels.SHOP:
                switchPanel(new Shop(this, user).getPanel());
                window.setJMenuBar(createMenuBar("Shop", () -> goTo(Panels.MENU, user)));
                break;
            case Panels.TANK_ADDING:
                switchPanel(new TankAdding(this, user).getPanel());
                window.setJMenuBar(createMenuBar("Inventory", () -> goTo(Panels.INVENTORY, user)));
                break;
        }
    }

    @Override
    public void goToGame(User user, UserData currentUserData, Client client, CopyOnWriteArrayList<UserData> users, Server server) {
        if (user == null || users == null || users.isEmpty()) return;

        window.setJMenuBar(createMenuBar("Enjoy Playing", null));
        switchPanel(new Game(this, user, currentUserData, client, users, server).getPanel());
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
