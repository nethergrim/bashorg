package com.nethergrim.bashorg;

/**
 * @author andrej on 19.06.15.
 */
public class ThemeUtils {

    public static boolean isADarkTheme() {
        return false; // TODO implement shared preferences here
    }

    public static void setDarkTheme(boolean dark) {
        // TODO
    }

    public static int getFontSize() {
        return 16;
    }

    public static void setFontSize(float size) {
        // TODO
    }

    public static float pxToSp(float px) {
        float scaledDensity = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static float spTopx(float sp) {
        float scaledDensity = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }
}
