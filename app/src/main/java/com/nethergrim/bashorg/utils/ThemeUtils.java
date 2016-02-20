package com.nethergrim.bashorg.utils;

import android.support.annotation.NonNull;

import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.model.Quote;

/**
 * @author andrej on 19.06.15.
 */
public class ThemeUtils {

    @NonNull
    public static ThemeType getCurrentTheme() {
        return Prefs.getCurrentTheme();
    }

    public static void setCurrentTheme(ThemeType t) {
        Prefs.setCurrentTheme(t);
    }

    public static int getFontSize() {
        return Prefs.getFontSize();
    }

    public static void setFontSize(int size) {
        Prefs.setFontSize(size);
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

    public static boolean isThemeBought(@NonNull ThemeType t) {
        return true; // all themes are free from now
//        if (PurchasesUtils.debugPurchasesMode()) {
//            return true;
//        }
//        switch (t) {
//            case DARK:
//                return InAppBillingServiceHolder.mBoughtSkus != null && InAppBillingServiceHolder.mBoughtSkus.contains(PurchasesUtils.ID_DARK_THEME);
//            default:
//                return true;
//        }
    }

    public static boolean isThemeEnabledNow(@NonNull ThemeType t) {
        return getCurrentTheme().equals(t);
    }
}
