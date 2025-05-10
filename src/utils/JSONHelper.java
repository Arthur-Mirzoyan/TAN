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

/**
 * Utility class for reading, writing, and manipulating JSON files using org.json.
 */
public class JSONHelper {

    /**
     * Parses a JSON file and converts a specified JSON array into a list of objects using a creator function.
     *
     * @param path          path to the JSON file
     * @param JSONArrayName name of the JSON array to parse
     * @param creator       function to convert each JSONObject into an object of type T
     * @param <T>           type of the objects to return
     * @return a list of parsed objects
     */
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

    /**
     * Reads a JSON file and applies a function to each element in the specified array.
     *
     * @param path          path to the JSON file
     * @param JSONArrayName name of the array in the JSON file
     * @param function      function to apply to each JSONObject
     * @param <T>           result type of the function (ignored)
     * @return the root JSONObject, or null if an error occurs
     */
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

    /**
     * Appends a new JSONObject to the specified array in the JSON file.
     *
     * @param path          path to the JSON file
     * @param JSONArrayName name of the array in the JSON file
     * @param json          the JSONObject to add
     */
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

    /**
     * Safely retrieves a value from a JSONObject, returning a default if not found or type mismatch.
     *
     * @param jsonObject   the JSON object to query
     * @param key          the key to look up
     * @param defaultValue value to return if key is missing or invalid
     * @param <T>          expected return type
     * @return the value or the default if not found or incorrect type
     */
    public static <T> T getValue(JSONObject jsonObject, String key, T defaultValue) {
        try {
            return (T) jsonObject.get(key);
        } catch (JSONException | ClassCastException e) {
            return defaultValue;
        }
    }

    /**
     * Updates an existing JSONObject within a JSON array based on a key match.
     *
     * @param path          path to the JSON file
     * @param JSONArrayName name of the array
     * @param key           field to compare
     * @param keyName       expected value to match
     * @param json          the new object to replace the matched entry
     */
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