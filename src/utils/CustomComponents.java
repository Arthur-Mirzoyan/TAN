package utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class CustomComponents {
    public static JTextField inputBox() {
        JTextField input = new JTextField();

        input.setFont(Values.MEDIUM_FONT);
        input.setForeground(Values.PRIMARY_COLOR);
        input.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return input;
    }

    public static JPasswordField passwordInputBox() {
        JPasswordField input = new JPasswordField();

        input.setEchoChar('.');
        input.setFont(Values.MEDIUM_FONT);
        input.setForeground(Values.PRIMARY_COLOR);
        input.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return input;
    }

    // Accepts only digits
    // The total value can have a length of at most 3
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

    public static JButton button(String name) {
        JButton button = new JButton(name);

        button.setFont(Values.MEDIUM_FONT);
        button.setForeground(Values.TERTIARY_COLOR);
        button.setBackground(Values.PRIMARY_COLOR);

        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return button;
    }

    public static JLabel label(String name) {
        JLabel label = new JLabel(name);
        label.setFont(Values.MEDIUM_FONT);
        label.setForeground(Values.PRIMARY_COLOR);

        return label;
    }
}
