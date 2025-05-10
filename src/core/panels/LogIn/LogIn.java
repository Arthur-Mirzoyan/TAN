package core.panels.LogIn;

import core.exceptions.UserNotFoundException;
import entities.user.User;
import utils.HashUtil;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The {@code LogIn} class manages the login interface and authentication logic.
 * It validates user credentials, handles login errors, and navigates the user to the appropriate panel.
 */
public class LogIn {
    private PanelListener panelListener;
    private LogInPanel scene;

    /**
     * Constructs a {@code LogIn} controller to handle user authentication and UI interactions.
     *
     * @param listener the panel listener used to navigate between screens
     * @param users    the list of registered users to authenticate against
     */
    public LogIn(PanelListener listener, ArrayList<User> users) {
        scene = new LogInPanel();
        panelListener = listener;

        scene.getSignUpButton().addActionListener(e -> panelListener.goTo(Panels.SIGNUP));
        scene.getLogInButton().addActionListener(e -> logIn(users));
    }

    /**
     * Returns the main panel for this login view.
     *
     * @return the panel for rendering the login UI
     */
    public JPanel getPanel() {
        return scene.getPanel();
    }

    /**
     * Validates the user-provided login credentials and navigates to the menu if successful.
     * Displays error messages for missing fields or failed login attempts.
     *
     * @param users the list of users to authenticate against
     */
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

    /**
     * Searches for a matching user by comparing the entered username and hashed password.
     *
     * @param users the list of users to search through
     * @return the matching {@code User} object if credentials are valid
     * @throws UserNotFoundException if no match is found
     */
    private User getUserWith(ArrayList<User> users) throws UserNotFoundException {
        String username = scene.getUsernameField().getText();
        String passwordHash = HashUtil.toMD5(scene.getPasswordField().getText());

        for (User user : users)
            if (user.getUsername().equals(username) && user.getPassword().equals(passwordHash))
                return user;

        throw new UserNotFoundException();
    }
}
