package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Values {
    public static Font CUSTOM_FONT;

    static {
        try {
            CUSTOM_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/font.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(CUSTOM_FONT);
        } catch (IOException | FontFormatException e) {
            CUSTOM_FONT = new Font("Arial", Font.BOLD, 16);
        }
    }

    public static final Font LARGE_FONT = CUSTOM_FONT.deriveFont(20f);
    public static final Font MEDIUM_FONT = CUSTOM_FONT.deriveFont(16f);
    public static final Font SMALL_FONT = CUSTOM_FONT.deriveFont(12f);

    public static final Color PRIMARY_COLOR = new Color(68, 54, 39);
    public static final Color SECONDARY_COLOR = new Color(239, 220, 171);
    public static final Color TERTIARY_COLOR = new Color(242, 246, 208);
    public static final Color ACCENT_COLOR = new Color(217, 131, 36);
}