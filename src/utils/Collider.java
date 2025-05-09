package utils;

import java.awt.*;

public abstract class Collider {
    protected Dimension dimension;
    protected Point position; // midpoint

    private Point[] corners;
    private double rotation;

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

    public Collider(Point position) {
        this(position, new Dimension());
    }

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

    public Collider(Point position) {
        this(position, new Dimension(0, 0));
    }

    public void setPosition(Point position) {
        this.position = position;
        updateCorners(); // Important for accurate collision detection
    }

    protected void setRotation(double rotation) {
        this.rotation = -Math.toRadians(rotation);
        updateCorners();
    }

    protected void setDimension(Dimension dimension) {
        this.dimension = dimension;
        updateCorners();
    }

    protected Point getPosition() {
        return new Point(position);
    }

    protected Point[] getCorners() {
        Point[] res = new Point[4];

        for (int i = 0; i < 4; i++) res[i] = new Point(corners[i]);

        return res;
    }

    public boolean collidesWith(Collider other) {
        Point A = corners[3];
        Point B = corners[2];
        Point D = corners[0];

        Point vectorAB = new Point(B.getX() - A.getX(), B.getY() - A.getY());
        Point vectorAD = new Point(D.getX() - A.getX(), D.getY() - A.getY());

        double dotAB_AB = vectorAB.getVectorDotProduct(vectorAB);
        double dotAD_AD = vectorAD.getVectorDotProduct(vectorAD);
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

        return other.collidesWith(this);
    }

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
