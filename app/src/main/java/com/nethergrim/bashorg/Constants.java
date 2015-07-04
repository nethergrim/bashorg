package com.nethergrim.bashorg;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ScaleXSpan;

/**
 * Created by nethergrim on 28.11.2014.
 */
public class Constants {

    public static final String EXTRA_PAGE_NUMBER = "com.nethergrim.bashorg.web.extra.PAGE_NUMBER";
    public static final String ACTION_FETCH_PAGE = "com.nethergrim.bashorg.web.action.FETCH_PAGE";
    public static final String ACTION_FETCH_TOP_PAGE = "com.nethergrim.bashorg.web.action.FETCH_TOP_PAGE";
    public static final String ACTION_FETCH_RANDOM_PAGE = "com.nethergrim.bashorg.web.action.FETCH_RANDOM_PAGE";
    public static final String ACTION_FETCH_ABYSS = "com.nethergrim.bashorg.web.action.FETCH_ABYSS";
    public static final String ACTION_SHARE_QUOTE = "com.nethergrim.bashorg.web.action.SHARE_QUOTE";
    public static final String EXTRA_QUOTE = "com.nethergrim.bashorg.web.extra.QUOTE_ID";
    public static final String URI_QUOTE = "content://com.nethergrim.bashorg.web.uri.QUOTE";
    public static final String URL_BASHORG_PAGE = "http://bash.im/index/";
    public static final String URL_BASHORG_RANDOM_PAGE = "http://bash.im/random";
    public static final String ROBOTO_LIGHT = "fonts/Roboto-Light.ttf";
    public static final String ROBOTO_THIN = "fonts/Roboto-Thin.ttf";
    public static final String ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";
    public static final String EXTRA_LIMIT = "com.nethergrim.bashorg.web.extra.LIMIT";
    public static final String EXTRA_ABYSS_PAGE = "com.nethergrim.bashorg.web.extra.ABYSS_PAGE";
    public static final String EXTRA_QUOTE_SELECTION = "com.nethergrim.bashorg.web.extra.QUOTE_SELECTION";
    public static final int DEFAULT_PAGE_SIZE = 50;
    public static final String LINK_TO_PLAY_MARKET = "https://play.google.com/store/apps/details?id=com.nethergrim.bashorg";
    public static final String URL_BASHORG_TOP = "http://bash.im/byrating/";
    public static final int PAGE_MAX = Integer.MAX_VALUE;
    public static float density;


    public static Spannable applyKerning(CharSequence src, float kerning) {
        if (src == null) return null;
        final int srcLength = src.length();
        if (srcLength < 2) return src instanceof Spannable
                ? (Spannable) src
                : new SpannableString(src);

        final String nonBreakingSpace = "\u00A0";
        final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                ? (SpannableStringBuilder) src
                : new SpannableStringBuilder(src);
        for (int i = src.length() - 1; i >= 1; i--) {
            builder.insert(i, nonBreakingSpace);
            builder.setSpan(new ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }
}
