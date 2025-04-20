package core.panels.SignUp;

import utils.CustomComponents;
import utils.Values;

import javax.swing.*;
import java.awt.*;

public class SignUpPanel {
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JButton signUpButton;

    public SignUpPanel() {
        panel = new JPanel();

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(generateLoginBox(), BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    private JPanel generateLoginBox() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeBackLabel = CustomComponents.label("CREATE ACCOUNT");
        welcomeBackLabel.setFont(Values.LARGE_FONT);
        welcomeBackLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = CustomComponents.label("Username");
        usernameField = CustomComponents.inputBox();

        JLabel passwordLabel = CustomComponents.label("Password");
        passwordField = CustomComponents.passwordInputBox();

        logInButton = CustomComponents.button("Sign Up");

        signUpButton = new JButton("Already have an account? Log In!");
        signUpButton.setForeground(Values.PRIMARY_COLOR);
        signUpButton.setFont(Values.SMALL_FONT);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);

        // Grid
        gbc.ipadx = 200;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        panel.add(welcomeBackLabel, gbc);

        JComponent[][] components = {{usernameLabel, usernameField}, {passwordLabel, passwordField}, {logInButton, signUpButton}};

        for (int i = 0; i < components.length; i++) {
            JPanel box = new JPanel(new GridBagLayout());
            GridBagConstraints innerGbc = new GridBagConstraints();

            innerGbc.fill = GridBagConstraints.HORIZONTAL;
            innerGbc.weightx = 1.0;

            box.add(components[i][0], innerGbc);
            innerGbc.gridy = 1;
            box.add(components[i][1], innerGbc);

            gbc.gridy = i + 1;
            box.setOpaque(false);

            panel.add(box, gbc);
        }

        return panel;
    }
}