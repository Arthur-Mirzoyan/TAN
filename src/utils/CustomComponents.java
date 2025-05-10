package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@code CustomComponents} provides factory methods to create customized Swing UI components
 * with consistent styling, input validation, and interaction behavior across the application.
 */
public abstract class CustomComponents {
    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

    /**
     * Creates a standard styled text input field that blocks space characters.
     *
     * @return a customized {@code JTextField}
     */
    public static JTextField inputBox() {
        JTextField input = new JTextField();

        input.setFont(Values.MEDIUM_FONT);
        input.setForeground(Values.PRIMARY_COLOR);
        input.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        input.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_SPACE) e.consume();
            }
        });

        return input;
    }

    /**
     * Creates a styled password input field that masks characters and blocks space characters.
     *
     * @return a customized {@code JPasswordField}
     */
    public static JPasswordField passwordInputBox() {
        JPasswordField input = new JPasswordField();

        input.setEchoChar('+');
        input.setFont(Values.MEDIUM_FONT);
        input.setForeground(Values.PRIMARY_COLOR);
        input.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        input.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_SPACE) e.consume();
            }
        });

        return input;
    }

    /**
     * Creates a numeric-only input box with a maximum length of 3 digits.
     *
     * @return a customized {@code JTextField} for numeric input
     */
    public static JTextField numericInputBox() {
        JTextField input = inputBox();

        input.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) ||
                        (input.getText().length() > 2))
                    e.consume();
            }
        });

        return input;
    }

    /**
     * Creates a styled button with consistent colors and cursor behavior.
     *
     * @param name the label to display on the button
     * @return a customized {@code JButton}
     */
    public static JButton button(String name) {
        JButton button = new JButton(name);

        button.setFont(Values.MEDIUM_FONT);
        button.setForeground(Values.TERTIARY_COLOR);
        button.setBackground(Values.PRIMARY_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(DEFAULT_CURSOR);
            }
        });


        return button;
    }

    /**
     * Creates a styled label with standard font and color.
     *
     * @param name the text to display in the label
     * @return a customized {@code JLabel}
     */
    public static JLabel label(String name) {
        JLabel label = new JLabel(name);
        label.setFont(Values.MEDIUM_FONT);
        label.setForeground(Values.PRIMARY_COLOR);

        return label;
    }

    /**
     * Creates a modal loading dialog with a message and a progress bar.
     * Includes a cancel button that closes the dialog and invokes the given action.
     *
     * @param message  the message to display
     * @param onClose  the action to perform when the dialog is closed
     * @return a configured {@code JDialog}
     */
    public static JDialog loadingDialog(String message, Runnable onClose) {
        JDialog dialog = new JDialog((Frame) null, "Please Wait...", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel loadingLabel = new JLabel(message);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(Values.TERTIARY_COLOR);
        progressBar.setForeground(Values.PRIMARY_COLOR);

        JButton exitButton = button("Exit Game");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setSelected(false);
        exitButton.addActionListener(e -> {
            dialog.dispose();
            onClose.run();
        });

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(loadingLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(exitButton);

        dialog.setContentPane(panel);

        return dialog;
    }
}
