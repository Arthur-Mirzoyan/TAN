package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Function;

public class JSONHelper {
    public static <T> ArrayList<T> parse(String path, String JSONArrayName, Function<JSONObject, T> creator) {
        ArrayList<T> res = new ArrayList<>();

        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray arrayJSON = jsonObject.getJSONArray(JSONArrayName);

            for (int i = 0; i < arrayJSON.length(); i++)
                res.add(creator.apply(arrayJSON.getJSONObject(i)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static <T> JSONObject read(String path, String JSONArrayName, Function<JSONObject, T> function) {
        JSONObject jsonObject;

        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonObject = new JSONObject(tokener);

            JSONArray arrayJSON = jsonObject.getJSONArray(JSONArrayName);

            for (int i = 0; i < arrayJSON.length(); i++)
                function.apply(arrayJSON.getJSONObject(i));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;
    }

    public static void write(String path, String JSONArrayName, JSONObject json) {
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray arrayJSON = jsonObject.getJSONArray(JSONArrayName);
            arrayJSON.put(json);

            Files.write(Path.of(path), jsonObject.toString(2).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getValue(JSONObject jsonObject, String key, T defaultValue) {
        try {
            return (T) jsonObject.get(key);
        } catch (JSONException | ClassCastException e) {
            return defaultValue;
        }
    }

    public static void update(String path, String JSONArrayName, String key, String keyName, JSONObject json) {
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray arrayJSON = jsonObject.getJSONArray(JSONArrayName);

            for (int i = 0; i < arrayJSON.length(); i++) {
                JSONObject obj = arrayJSON.getJSONObject(i);
                if (obj.getString(key).equals(keyName)) {
                    arrayJSON.put(i, json);
                    break;
                }
            }

            Files.write(Path.of(path), jsonObject.toString(2).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}