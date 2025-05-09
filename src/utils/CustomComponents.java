package utils;

import entities.tank.components.TankCannon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
