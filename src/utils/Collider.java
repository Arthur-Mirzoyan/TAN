package utils;

import java.awt.*;

/**
 * The {@code Collider} class represents a rectangular entity in 2D space that can detect
 * collisions with other {@code Collider} objects. It handles position, dimension, rotation,
 * and collision detection based on the separating axis theorem (SAT).
 * <p>
 * Each collider is defined by a midpoint, dimension, and a set of rotated corner points.
 */
public abstract class Collider {
    protected Dimension dimension;
    protected Point position; // midpoint

    private Point[] corners;
    private double rotation;

    /**
     * Constructs a new {@code Collider} with a specified position and dimension.
     *
     * @param position  the midpoint position of the collider
     * @param dimension the width and height of the collider
     */
    public Collider(Point position, Dimension dimension) {
        this.position = position;
        this.dimension = dimension;

        int x = position.getX();
        int y = position.getY();
        int widthHalf = (int) dimension.getWidth() / 2;
        int heightHalf = (int) dimension.getHeight() / 2;

        this.corners = new Point[]{
                new Point(x - widthHalf, y - heightHalf), // Top-Left       D
                new Point(x + widthHalf, y - heightHalf), // Top-Right      C
                new Point(x + widthHalf, y + heightHalf), // Bottom-Right   B
                new Point(x - widthHalf, y + heightHalf)  // Bottom-Left    A
        };

        updateCorners();
    }

    /**
     * Constructs a collider with only position; uses a default dimension.
     *
     * @param position the midpoint position
     */
    public Collider(Point position) {
        this(position, new Dimension());
    }

    /**
     * Constructs a deep copy of another {@code Collider}.
     *
     * @param other the collider to copy
     */
    protected Collider(Collider other) {
        if (other == null) {
            System.out.println("Collider other cannot be null");
            System.exit(0);
        }

        this.position = new Point(other.position);
        this.dimension = new Dimension(other.dimension);
        this.corners = new Point[other.corners.length];

        for (int i = 0; i < corners.length; i++)
            corners[i] = new Point(other.corners[i]);
    }

    /**
     * Sets the new midpoint position of this collider.
     *
     * @param position the new position
     */
    public void setPosition(Point position) {
        this.position = position;
        updateCorners();
    }

    /**
     * Sets the rotation of this collider in degrees.
     *
     * @param rotation the angle in degrees
     */
    protected void setRotation(double rotation) {
        this.rotation = -Math.toRadians(rotation);
        updateCorners();
    }

    /**
     * Sets a new dimension for this collider.
     *
     * @param dimension the new width and height
     */
    protected void setDimension(Dimension dimension) {
        this.dimension = dimension;
        updateCorners();
    }

    /**
     * Returns a copy of the collider's midpoint position.
     */
    public Point getPosition() {
        return new Point(position);
    }

    /**
     * Returns a copy of this collider's four corners in rotated screen coordinates.
     */
    protected Point[] getCorners() {
        Point[] res = new Point[4];

        for (int i = 0; i < 4; i++) res[i] = new Point(corners[i]);

        return res;
    }

    /**
     * Checks whether this collider intersects with another.
     * Uses a simplified version of the Separating Axis Theorem.
     *
     * @param other another collider to test against
     * @return true if the colliders overlap; false otherwise
     */
    public boolean collidesWith(Collider other) {
        Point A = corners[3];
        Point B = corners[2];
        Point D = corners[0];

        Point vectorAB = new Point(B.getX() - A.getX(), B.getY() - A.getY());
        Point vectorAD = new Point(D.getX() - A.getX(), D.getY() - A.getY());

        double dotAB_AB = vectorAB.getVectorDotProduct(vectorAB);
        double dotAD_AD = vectorAD.getVectorDotProduct(vectorAD);

        if (dotAB_AB == 0 || dotAD_AD == 0) return false;

        for (Point P : other.corners) {
            Point vectorAP = new Point(P.getX() - A.getX(), P.getY() - A.getY());

            double dotAP_AB = vectorAP.getVectorDotProduct(vectorAB);
            double dotAP_AD = vectorAP.getVectorDotProduct(vectorAD);

            if ((0 <= dotAP_AB && dotAP_AB <= dotAB_AB) && (0 <= dotAP_AD && dotAP_AD <= dotAD_AD))
                return true;
        }

        Point A2 = other.corners[3];
        Point B2 = other.corners[2];
        Point D2 = other.corners[0];

        Point vectorAB2 = new Point(B2.getX() - A2.getX(), B2.getY() - A2.getY());
        Point vectorAD2 = new Point(D2.getX() - A2.getX(), D2.getY() - A2.getY());

        double dotAB2_AB2 = vectorAB2.getVectorDotProduct(vectorAB2);
        double dotAD2_AD2 = vectorAD2.getVectorDotProduct(vectorAD2);

        if (dotAB2_AB2 == 0 || dotAD2_AD2 == 0) return false;

        for (Point P : this.corners) {
            Point vectorAP2 = new Point(P.getX() - A2.getX(), P.getY() - A2.getY());

            double dotAP2_AB2 = vectorAP2.getVectorDotProduct(vectorAB2);
            double dotAP2_AD2 = vectorAP2.getVectorDotProduct(vectorAD2);

            if ((0 <= dotAP2_AB2 && dotAP2_AB2 <= dotAB2_AB2) && (0 <= dotAP2_AD2 && dotAP2_AD2 <= dotAD2_AD2))
                return true;
        }

        return false;
    }

    /**
     * Checks whether this collider fits within the bounds of the map and does not intersect any walls.
     *
     * @param map the game map
     * @return true if the collider is in a free space; false otherwise
     */
    public boolean isFreeOfCollisions(Map map) {
        int x = position.getX();
        int y = position.getY();
        double widthHalf = dimension.getWidth() / 2;
        double heightHalf = dimension.getHeight() / 2;

        if (x - widthHalf < 0 || x + widthHalf > map.getWidth()) return false;
        if (y - heightHalf < 0 || y + heightHalf > map.getHeight()) return false;

        int cellSize = map.getCellSize();
        byte[][] layout = map.getLayout();

        for (Point corner : corners) {
            int row = corner.getY() / cellSize;
            int col = corner.getX() / cellSize;

            if (layout[row][col] == Map.WALL) return false;
        }

        return true;
    }

    /**
     * Recalculates the corner positions based on current position, rotation, and dimensions.
     */
    protected void updateCorners() {
        int centerX = position.getX();
        int centerY = position.getY();
        int halfWidth = (int) dimension.getWidth() / 2;
        int halfHeight = (int) dimension.getHeight() / 2;

        double cos = Math.cos(rotation);
        double sin = Math.sin(rotation);

        int[] relX = {-halfWidth, halfWidth, halfWidth, -halfWidth};
        int[] relY = {-halfHeight, -halfHeight, halfHeight, halfHeight};

        corners = new Point[4];

        for (int i = 0; i < 4; i++) {
            double rotatedX = relX[i] * cos - relY[i] * sin;
            double rotatedY = relX[i] * sin + relY[i] * cos;

            corners[i] = new Point((int) (centerX + rotatedX), (int) (centerY + rotatedY));
        }
    }
}
