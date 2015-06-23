package com.nethergrim.bashorg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nethergrim.bashorg.utils.ThemeType;

/**
 * Created by andrey_drobyazko on 08.12.14 20:00.
 */
public class Prefs {

    public static final String KEY_CHARGING = "charging";
    public static final String KEY_FONT_SIZE = "font_size";
    public static final int DEFAULT_FONT_SIZE = 16;
    public static final String KEY_CURRENT_THEME = "dark_theme_enabled";
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

    public static int getFontSize() {
        return prefs.getInt(KEY_FONT_SIZE, DEFAULT_FONT_SIZE);
    }

    public static void setFontSize(int newFontSize) {
        prefs.edit().putInt(KEY_FONT_SIZE, newFontSize).apply();
    }

    public static ThemeType getCurrentTheme() {
        return ThemeType.getTypeForCode(prefs.getInt(KEY_CURRENT_THEME, ThemeType.LIGHT.getCode()));
    }

    public static void setCurrentTheme(ThemeType t) {
        prefs.edit().putInt(KEY_CURRENT_THEME, t.getCode()).apply();
    }
}
