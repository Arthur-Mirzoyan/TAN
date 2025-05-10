package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The {@code Values} class provides static constants used for UI styling,
 * including custom fonts and color palettes. It attempts to load a custom
 * pixel-style font, falling back to a default system font if needed.
 * <p>
 * This class is intended for consistent theme and style management.
 */
public abstract class Values {

    /** The custom game font loaded from a TTF file. */
    public static Font CUSTOM_FONT;

    static {
        try {
            CUSTOM_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Pixellari.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(CUSTOM_FONT);
        } catch (IOException | FontFormatException e) {
            CUSTOM_FONT = new Font("Arial", Font.BOLD, 16);
        }
    }

    public static final Font EXTRA_SUPER_LARGE_FONT = CUSTOM_FONT.deriveFont(40f);
    public static final Font EXTRA_LARGE_FONT = CUSTOM_FONT.deriveFont(28f);
    public static final Font LARGE_FONT = CUSTOM_FONT.deriveFont(20f);
    public static final Font MEDIUM_FONT = CUSTOM_FONT.deriveFont(16f);
    public static final Font SMALL_FONT = CUSTOM_FONT.deriveFont(12f);


    public static final Color PRIMARY_COLOR = new Color(68, 54, 39);
    public static final Color SECONDARY_COLOR = new Color(239, 220, 171);
    public static final Color TERTIARY_COLOR = new Color(242, 246, 208);
    public static final Color ACCENT_COLOR = new Color(217, 131, 36);
}