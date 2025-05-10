package core;

import javax.swing.*;

/**
 * The {@code Launcher} class serves as the entry point for the application.
 * It initializes and displays the main application window using Swing.
 */
public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();

            mainWindow.show();
        });
    }
}
