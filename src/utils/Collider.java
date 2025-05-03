package utils;

public class Collider {
    private Point position; // midpoint
    private Dimension dimension;
    private Point[] corners;

    public Collider(Point position, Dimension dimension) {
        this.position = position;
        this.dimension = dimension;

        int x = position.getX();
        int y = position.getY();
        int widthHalf = (int) dimension.getWidth();
        int heightHalf = (int) dimension.getHeight();

        this.corners = new Point[]{
                new Point(x - widthHalf, y - heightHalf), // Top-Left       D
                new Point(x + widthHalf, y - heightHalf), // Top-Right      C
                new Point(x + widthHalf, y + heightHalf), // Bottom-Right   B
                new Point(x - widthHalf, y + heightHalf)  // Bottom-Left    A
        };
    }

    public boolean collidesWith(Collider other) {
        Point A = corners[3];
        Point B = corners[2];
        Point D = corners[0];

        Point vectorAB = new Point(B.getX() - A.getX(), B.getY() - A.getY());
        Point vectorAD = new Point(D.getX() - A.getX(), D.getY() - A.getY());

        int dotAB_AB = vectorAB.getVectorProduct(vectorAB);
        int dotAD_AD = vectorAB.getVectorProduct(vectorAD);

        for (Point P : other.corners) {
            Point vectorAP = new Point(P.getX() - A.getX(), P.getY() - A.getY());

            int dotAP_AB = vectorAP.getVectorProduct(vectorAB);
            int dotAP_AD = vectorAP.getVectorProduct(vectorAD);

            if ((0 <= dotAP_AB && dotAP_AB <= dotAB_AB) && (0 <= dotAP_AD && dotAP_AD <= dotAD_AD)) return true;
        }

        return false;
    }
}
