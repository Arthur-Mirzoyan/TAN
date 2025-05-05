package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

public class ImageDrawer {
    private Image image;

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

    public Image getImage() {
        return image;
    }

    public Image getScaledImage(int width, int height) {
        if (image == null) return null;
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public Image getScaledImage(double scale) {
        if (image == null) return null;
        return image.getScaledInstance(
                (int) (image.getWidth(null) * scale),
                (int) (image.getHeight(null) * scale),
                Image.SCALE_SMOOTH);
    }
}