package utils;

import entities.tank.components.TankCannon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Function;

public abstract class CustomComponents {
    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

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

    public static JPasswordField passwordInputBox() {
        JPasswordField input = new JPasswordField();

        input.setEchoChar('.');
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

    public static JLabel label(String name) {
        JLabel label = new JLabel(name);
        label.setFont(Values.MEDIUM_FONT);
        label.setForeground(Values.PRIMARY_COLOR);

        return label;
    }

    public static JButton backButton() {
        ImageIcon icon = new ImageIcon("assets/img/arrow.png");
        JButton button = new JButton(icon);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

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
}
