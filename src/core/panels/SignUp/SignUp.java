package core.panels.SignUp;

import core.exceptions.UserNotFoundException;
import entities.user.User;
import utils.HashUtil;
import utils.JSONHelper;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The {@code SignUp} class handles the user registration process in the application.
 * It manages user input, validation, and account creation by writing new user data
 * to persistent storage. It also handles UI transitions.
 */
public class SignUp {
    private PanelListener panelListener;
    private SignUpPanel scene;

    /**
     * Constructs a {@code SignUp} controller that initializes the sign-up UI and its event logic.
     *
     * @param listener the panel listener to navigate between screens
     * @param users    the list of existing users for duplicate checks
     */
    public SignUp(PanelListener listener, ArrayList<User> users) {
        scene = new SignUpPanel();
        panelListener = listener;

        scene.getSignUpButton().addActionListener(e -> listener.goTo(Panels.LOGIN));
        scene.getLogInButton().addActionListener(e -> signUp(users));
    }

    /**
     * Returns the visual panel representing the sign-up form.
     *
     * @return the sign-up UI panel
     */
    public JPanel getPanel() {
        return scene.getPanel();
    }

    /**
     * Handles the sign-up logic: validates input, checks for duplicate usernames,
     * hashes the password, and saves the new user if valid.
     *
     * @param users the list of existing users to check for duplicates
     */
    private void signUp(ArrayList<User> users) {
        String username = scene.getUsernameField().getText();
        String password = scene.getPasswordField().getText();
        String passwordHash = HashUtil.toMD5(password);

        if (username.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Username missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else if (password.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Password missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            try {
                getUserWith(users);
                JOptionPane.showMessageDialog(getPanel(), "User " + username + " already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (UserNotFoundException e) {
                User user = new User(username, passwordHash);
                JSONHelper.write("src/data/users.json", "users", user.toJSON());
                panelListener.goTo(Panels.MENU, user);
            }
        }
    }

    /**
     * Searches the user list for a matching username.
     *
     * @param users the list of users to search
     * @return the found user if present
     * @throws UserNotFoundException if the user is not in the list
     */
    private User getUserWith(ArrayList<User> users) throws UserNotFoundException {
        String username = scene.getUsernameField().getText();

        for (User user : users)
            if (user.getUsername().equals(username))
                return user;

        throw new UserNotFoundException();
    }
}
