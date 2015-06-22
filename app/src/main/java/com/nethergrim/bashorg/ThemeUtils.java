package com.nethergrim.bashorg;

import com.nethergrim.bashorg.model.Quote;

/**
 * @author andrej on 19.06.15.
 */
public class ThemeUtils {

    public static boolean isADarkTheme() {
        return Prefs.isDarkThemeEnabled();
    }

    public static void setDarkTheme(boolean dark) {
        Prefs.setDarkThemeEnabled(dark);
    }

    public static int getFontSize() {
        return Prefs.getFontSize();
    }

    public static void setFontSize(int size) {
        Prefs.setFontSize(size);
    }

    public static float pxToSp(float px) {
        float scaledDensity = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static float spTopx(float sp) {
        float scaledDensity = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    public static Quote getDefaultQuote() {
        Quote mDefaultQuote;
        mDefaultQuote = Quote.newInstance();
        mDefaultQuote.setId(0);
        mDefaultQuote.setLiked(false);
        mDefaultQuote.setRating(100500);
        mDefaultQuote.setDate("12.12.2012");
        mDefaultQuote.setText("Она: ответь мне, только честно, да или нет, хорошо?\n" +
                "Он: спрашивай\n" +
                "Она: почему мужчины смеются над блондинками?\n" +
                "Он: да");

        return mDefaultQuote;
    }
}
