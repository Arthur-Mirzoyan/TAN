package core;

import javax.swing.*;

public class MainWindow {
    private JFrame window;

    public MainWindow() {
        initialize();
    }

    public void initialize() {
        window = new JFrame();

        window.setTitle("TAN - Terminate Advance Neutralise");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800, 450);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLayout(null);
    }

    public void show() {
        window.setVisible(true);
    }

    public void addPanel(JPanel panel) {
        window.setContentPane(panel);
    }
}
