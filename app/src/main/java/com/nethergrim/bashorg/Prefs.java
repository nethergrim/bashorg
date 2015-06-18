package com.nethergrim.bashorg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by andrey_drobyazko on 08.12.14 20:00.
 */
public class Prefs {

    public static final String KEY_CHARGING = "charging";
    private static final String SMALLEST_PAGE = "connection_couner";
    private static final String MAX_TOP_PAGE = "MAX_TOP_PAGE";
    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static long getLastPageNumber() {
        return prefs.getLong(Constants.EXTRA_PAGE_NUMBER, 1000000000);
    }

    public static void setLastPageNumber(long lastPageNumber) {
        prefs.edit().putLong(Constants.EXTRA_PAGE_NUMBER, lastPageNumber).apply();
    }

    public static int getSmallestLoadedPage() {
        return prefs.getInt(SMALLEST_PAGE, 10000000);
    }

    public static void setSmallestLoadedPage(int counter) {
        if (counter < getSmallestLoadedPage())
            prefs.edit().putInt(SMALLEST_PAGE, counter).apply();
    }

    public static int getMaxTopPage() {
        return prefs.getInt(MAX_TOP_PAGE, 1);
    }

    public static void setMaxTopPage(int number) {
        if (number > getMaxTopPage()) {
            prefs.edit().putInt(MAX_TOP_PAGE, number).apply();
        }
    }

    public static boolean isCharging() {
        return prefs.getBoolean(KEY_CHARGING, false);
    }

    public static void setCharging(boolean charging) {
        prefs.edit().putBoolean(KEY_CHARGING, charging).apply();
    }
}
