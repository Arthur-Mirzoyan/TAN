package utils;

import org.json.JSONObject;

public class Point {
    private int x;
    private int y;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public Point(JSONObject p) {
        this(p.getInt("x"), p.getInt("y"));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVectorDotProduct(Point p) {
        return x * p.x + y * p.y;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("x", x);
        json.put("y", y);

        return json;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }
}
