package utils;

import org.json.JSONObject;

/**
 * The {@code Point} class represents a 2D coordinate with integer x and y values.
 * It provides constructors for manual initialization, copying from another point,
 * or deserializing from a JSON object. Includes basic vector operations and utility methods.
 */
public class Point {
    private int x;
    private int y;

    /**
     * Constructs a {@code Point} at the origin (0, 0).
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Constructs a {@code Point} with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a copy of the specified {@code Point}.
     *
     * @param p the {@code Point} to copy
     */
    public Point(Point p) {
        this(p.x, p.y);
    }

    /**
     * Constructs a {@code Point} from a JSON object containing "x" and "y" keys.
     *
     * @param p the {@code JSONObject} containing the point data
     */
    public Point(JSONObject p) {
        this(p.getInt("x"), p.getInt("y"));
    }

    /**
     * Sets the x-coordinate of this point.
     *
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this point.
     *
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return the x value
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return the y value
     */
    public int getY() {
        return y;
    }

    /**
     * Computes the dot product between this point and another.
     *
     * @param p the other point
     * @return the scalar dot product of the two points
     */
    public int getVectorDotProduct(Point p) {
        return x * p.x + y * p.y;
    }

    /**
     * Returns a string representation of the point.
     *
     * @return a string in the form {@code Point{x=..., y=...}}
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Compares this point to another object for equality.
     *
     * @param obj the object to compare with
     * @return {@code true} if the other object is a {@code Point} with the same coordinates
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }
}
