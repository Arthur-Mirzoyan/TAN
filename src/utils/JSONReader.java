package utils;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.function.Function;

public class JSONReader {
    public static <T> ArrayList<T> parseJSON(String path, String JSONArrayName, Function<JSONObject, T> creator) {
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
}