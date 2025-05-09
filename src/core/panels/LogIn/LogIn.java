package core.panels.LogIn;

import core.exceptions.UserNotFoundException;
import entities.user.User;
import utils.HashUtil;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.util.ArrayList;

public class LogIn {
    private PanelListener panelListener;
    private LogInPanel scene;

    public LogIn(PanelListener listener, ArrayList<User> users) {
        scene = new LogInPanel();
        panelListener = listener;

        scene.getSignUpButton().addActionListener(e -> panelListener.goTo(Panels.SIGNUP));
        scene.getLogInButton().addActionListener(e -> logIn(users));
    }

    public JPanel getPanel() {
        return scene.getPanel();
    }

    private void logIn(ArrayList<User> users) {
        String username = scene.getUsernameField().getText();
        String password = scene.getPasswordField().getText();

        if (username.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Username missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else if (password.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Password missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            try {
                User user = getUserWith(users);
                panelListener.goTo(Panels.MENU, user);
            } catch (UserNotFoundException e) {
                JOptionPane.showMessageDialog(getPanel(), "User not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private User getUserWith(ArrayList<User> users) throws UserNotFoundException {
        String username = scene.getUsernameField().getText();
        String passwordHash = HashUtil.toMD5(scene.getPasswordField().getText());

        for (User user : users)
            if (user.getUsername().equals(username) && user.getPassword().equals(passwordHash))
                return user;

        throw new UserNotFoundException();
    }
}
