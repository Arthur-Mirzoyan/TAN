package core.panels.SignUp;

import utils.PanelListener;
import utils.Panels;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SignUp {
    private SignUpPanel scene;
    private String username;
    private String password;

    public SignUp(PanelListener listener) {
        scene = new SignUpPanel();

        username = "";
        password = "";

        scene.getSignUpButton().addActionListener(e -> listener.goTo(Panels.LOGIN));
        scene.getLogInButton().addActionListener(e -> signUp());

        addUsernameListeners(scene.getUsernameField());
        addPasswordListeners(scene.getPasswordField());
    }

    public JPanel getPanel() {
        return scene.getPanel();
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    private void signUp() {
        if (username.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Username missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else if (password.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Password missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(getPanel(), "Username: " + username + "\nPassword: " + password);
    }

    private void addUsernameListeners(JTextField usernameField) {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                handleChange();
            }

            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            private void handleChange() {
                setUsername(usernameField.getText());
            }
        });
    }

    private void addPasswordListeners(JPasswordField passwordField) {
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                handleChange();
            }

            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            private void handleChange() {
                setPassword(passwordField.getText());
            }
        });
    }
}
