package entities;

import org.json.JSONObject;
import org.json.JSONArray;

public class Map {
    private final int width;
    private final int height;
    private final byte[][] layout;

    public Map(int width, int height, byte[][] layout) {
        this.width = width;
        this.height = height;
        this.layout = layout;
    }

    public Map(JSONObject json) {
        JSONArray layout = json.getJSONArray("layout");

        byte[][] byteLayout = new byte[layout.length()][];

        for (int i = 0; i < layout.length(); i++) {
            JSONArray row = layout.getJSONArray(i);
            byte[] byteRow = new byte[row.length()];

            for (int j = 0; j < row.length(); j++) byteRow[j] = (byte) row.getInt(j);

            byteLayout[i] = byteRow;
        }

        this(json.getInt("width"), json.getInt("height"), byteLayout);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[][] getLayout() {
        return layout;
    }

    public void draw() {
    }
}
