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
    private static final String SMALLEST_PAGE = "connection_couner";
    private static final String MAX_TOP_PAGE = "MAX_TOP_PAGE";
    private static final String DATABASE_FILLED = "database_filled";

    public static void init(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLastPageNumber(long lastPageNumber){
        prefs.edit().putLong(Constants.EXTRA_PAGE_NUMBER, lastPageNumber).apply();
    }

    public static long getLastPageNumber(){
        return prefs.getLong(Constants.EXTRA_PAGE_NUMBER, 1000000000);
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

    public static void setSmallestLoadedPage(int counter){
        if (counter < getSmallestLoadedPage())
        prefs.edit().putInt(SMALLEST_PAGE, counter).apply();
    }

    public static int getSmallestLoadedPage(){
        return prefs.getInt(SMALLEST_PAGE, 10000000);
    }

    public static void setDatabaseFilled(boolean filled){
        prefs.edit().putBoolean(DATABASE_FILLED, filled).apply();
    }

    public static boolean isDatabaseFilled(){
        return prefs.getBoolean(DATABASE_FILLED, false);
    }

    public static void setMaxTopPage(int number){
        if (number > getMaxTopPage()){
            prefs.edit().putInt(MAX_TOP_PAGE, number).apply();
        }
    }

    public static int getMaxTopPage(){
        return prefs.getInt(MAX_TOP_PAGE, 1);
    }

    public static void setConnectedToWifi(boolean connected){
        prefs.edit().putBoolean(KEY_CONNECTED_TO_WIFI, connected).apply();
    }

    public static boolean isConnectedToWifi(){
        return prefs.getBoolean(KEY_CONNECTED_TO_WIFI, true);
    }

    public static void setCharging(boolean charging){
        prefs.edit().putBoolean(KEY_CHARGING, charging).apply();
    }

    public static boolean isCharging(){
        return prefs.getBoolean(KEY_CHARGING, false);
    }

    public static final String KEY_CHARGING = "charging";

    public static final String KEY_CONNECTED_TO_WIFI = "wifi_connected";
}
