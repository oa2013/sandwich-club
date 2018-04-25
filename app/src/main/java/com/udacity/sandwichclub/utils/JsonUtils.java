package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);

            Sandwich newSandwich  = new Sandwich();
            JSONObject nameObject = jsonObject.getJSONObject("name");
            newSandwich.setMainName(nameObject.getString("mainName"));

            JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownList = new ArrayList<String>();
            for(int i=0; i<alsoKnownAsArray.length(); i++){
                alsoKnownList.add(alsoKnownAsArray.getString(i));
            }
            newSandwich.setAlsoKnownAs(alsoKnownList);

            newSandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            newSandwich.setDescription(jsonObject.getString("description"));
            newSandwich.setImage(jsonObject.getString("image"));

            JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredientList = new ArrayList<String>();
            for(int i=0; i<ingredientArray.length(); i++){
                ingredientList.add(ingredientArray.getString(i));
            }
            newSandwich.setIngredients(ingredientList);

            return newSandwich;
        }
        catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
