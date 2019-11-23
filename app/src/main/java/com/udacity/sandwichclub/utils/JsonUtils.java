package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        /* Details for SW name */
        final String SW_NAME_DETAILS = "name";

        /* Name of SW */
        final String SD_NAME = "mainName";

        final String SD_ALIAS = "alsoKnownAs";

        final String SD_ORIGIN = "placeOfOrigin";

        final String SD_DESC = "description";

        final String SD_IMAGE = "image";

        final String SD_ING = "ingredients";

        JSONObject sandwichJO = new JSONObject(json);

        JSONObject sand_name = sandwichJO.getJSONObject(SW_NAME_DETAILS);

        String mainName = sand_name.getString(SD_NAME);
        JSONArray alsoKnownArray = sand_name.getJSONArray(SD_ALIAS);
        List<String> alsoKnownAs = jsonArrayToList(alsoKnownArray);

        String placeOfOrigin = sandwichJO.getString(SD_ORIGIN);
        String swDesc = sandwichJO.getString(SD_DESC);
        String swIamge = sandwichJO.getString(SD_IMAGE);

        JSONArray swIngredients = sandwichJO.getJSONArray(SD_ING);
        List<String> swIngred = jsonArrayToList(swIngredients);

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, swDesc, swIamge, swIngred);
    }

    public static List<String> jsonArrayToList(JSONArray rawArray) throws JSONException {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < rawArray.length(); i++) {
            list.add(rawArray.getString(i));
        }
        return list;
    }
}
