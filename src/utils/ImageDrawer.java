package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * Utility class to load, scale, and rotate images.
 */
public class ImageDrawer {
    private Image image;

    /**
     * Constructs an {@code ImageDrawer} by loading an image from the specified path.
     *
     * @param path the relative path to the image file
     */
    public ImageDrawer(String path) {
        loadImage(path);
    }

    private void loadImage(String path) {
        try {
            URL imageUrl = getClass().getClassLoader().getResource(path);

            if (imageUrl == null) {
                System.err.println("Image not found at: " + path);
                return;
            }

            try (ImageInputStream input = ImageIO.createImageInputStream(imageUrl.openStream())) {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

                if (!readers.hasNext()) {
                    System.err.println("No ImageReader found for: " + path);
                    return;
                }

                ImageReader reader = readers.next();
                reader.setInput(input);
                image = reader.read(0);
                reader.dispose();
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
    }

    /**
     * Returns the loaded image.
     *
     * @return the {@code Image}, or {@code null} if not loaded
     */
    public Image getImage() {
        return image;
    }

    /**
     * Returns the image scaled to the specified width and height.
     *
     * @param width  desired width
     * @param height desired height
     * @return scaled {@code Image}, or {@code null} if image is not loaded
     */
    public Image getScaledImage(int width, int height) {
        if (image == null) return null;
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /**
     * Returns the image scaled by the given scale factor.
     *
     * @param scale the scaling factor (e.g., 0.5 for half size)
     * @return scaled {@code Image}, or {@code null} if image is not loaded
     */
    public Image getScaledImage(double scale) {
        if (image == null) return null;
        return image.getScaledInstance(
                (int) (image.getWidth(null) * scale),
                (int) (image.getHeight(null) * scale),
                Image.SCALE_SMOOTH);
    }

    /**
     * Rotates the given image by the specified degrees clockwise.
     *
     * @param image   the image to rotate
     * @param degrees rotation angle in degrees (clockwise)
     * @return a new rotated {@code BufferedImage}
     */
    public static BufferedImage rotateImage(Image image, double degrees) {
        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();

        int newW = (int) Math.floor(w * cos + h * sin);
        int newH = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        AffineTransform at = new AffineTransform();
        at.translate((newW - w) / 2.0, (newH - h) / 2.0);
        at.rotate(radians, w / 2.0, h / 2.0);

        g2d.setTransform(at);
        g2d.drawImage(bufferedImage, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

}