package core.panels.SignUp;

import core.exceptions.UserNotFoundException;
import entities.user.User;
import utils.HashUtil;
import utils.JSONHelper;
import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;

public class SignUp {
    private PanelListener panelListener;
    private SignUpPanel scene;

    public SignUp(PanelListener listener, ArrayList<User> users) {
        scene = new SignUpPanel();
        panelListener = listener;

        scene.getSignUpButton().addActionListener(e -> listener.goTo(Panels.LOGIN));
        scene.getLogInButton().addActionListener(e -> signUp(users));
    }

    public JPanel getPanel() {
        return scene.getPanel();
    }

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
//                panelListener.goTo(Panels.MENU, user);
            } catch (UserNotFoundException e) {
                User user = new User(username, passwordHash);
                JSONHelper.write("src/data/users.json", "users", user.toJSON());
                panelListener.goTo(Panels.MENU, user);
            }
        }
    }

    private User getUserWith(ArrayList<User> users) throws UserNotFoundException {
        String username = scene.getUsernameField().getText();

        for (User user : users)
            if (user.getUsername().equals(username))
                return user;

        throw new UserNotFoundException();
    }
}
