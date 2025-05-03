package utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageDrawer {
    private Image image;

    public ImageDrawer(String path) {
        loadImage(path);
    }

    private void loadImage(String path) {
        try {
            // Using ClassLoader to get the resource
            URL imageUrl = getClass().getClassLoader().getResource(path);
            if (imageUrl == null) {
                System.err.println("Image not found at: " + path);
                return;
            }

            // Read the image using Toolkit
            image = Toolkit.getDefaultToolkit().createImage(imageUrl);

            // Wait for image to load
            MediaTracker tracker = new MediaTracker(new JPanel());
            tracker.addImage(image, 0);
            try {
                tracker.waitForAll();
            } catch (InterruptedException e) {
                System.err.println("Image loading interrupted");
            }
        } catch (Exception e) {
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