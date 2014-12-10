package com.nethergrim.bashorg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey_drobyazko on 08.12.14 20:00.
 */
public class Prefs {

    private static SharedPreferences prefs;

    public static void init(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLastPageNumber(long lastPageNumber){
        prefs.edit().putLong(Constants.EXTRA_PAGE_NUMBER, lastPageNumber).apply();
    }

    public static long getLastPageNumber(){
        return prefs.getLong(Constants.EXTRA_PAGE_NUMBER, 0l);
    }

    private static String convertListOfStringsToString(List<String> stringes){
        if (stringes == null) return "";
        JSONArray jsonArray = new JSONArray();
        for (String stringe : stringes) {
            jsonArray.put(stringe);
        }
        return jsonArray.toString();
    }

    private static List<String> convertStringToListOfStrings(String s){
        List<String> result = new ArrayList<String>();
        if (s.equals("")) return result;
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


}
