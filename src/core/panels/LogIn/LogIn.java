package core.panels.LogIn;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.Panels;
import utils.PanelListener;

public class LogIn {
    private PanelListener panelListener;
    private LogInPanel scene;
    private String username;
    private String password;

    public LogIn(PanelListener listener) {
        scene = new LogInPanel();
        panelListener = listener;

        username = "";
        password = "";

        scene.getSignUpButton().addActionListener(e -> panelListener.goTo(Panels.SIGNUP));
        scene.getLogInButton().addActionListener(e -> logIn());

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

    private void logIn() {
        if (username.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Username missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else if (password.isBlank())
            JOptionPane.showMessageDialog(getPanel(), "Password missing!!!", "Error", JOptionPane.ERROR_MESSAGE);
        else
            panelListener.goTo(Panels.MENU);
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
