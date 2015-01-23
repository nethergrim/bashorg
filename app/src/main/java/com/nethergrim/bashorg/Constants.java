package com.nethergrim.bashorg;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by nethergrim on 28.11.2014.
 */
public class Constants {

    public static final String EXTRA_PAGE_NUMBER = "com.nethergrim.bashorg.web.extra.PAGE_NUMBER";
    public static final String EXTRA_PAGE_IS_ALREADY_LOADED = "com.nethergrim.bashorg.web.extra.PAGE_ALREADY_LOADED";
    public static final String ACTION_FETCH_PAGE = "com.nethergrim.bashorg.web.action.FETCH_PAGE";
    public static final String ACTION_FETCH_TOP_PAGE = "com.nethergrim.bashorg.web.action.FETCH_TOP_PAGE";
    public static final String ACTION_SHARE_QUOTE = "com.nethergrim.bashorg.web.action.SHARE_QUOTE";
    public static final String EXTRA_QUOTE = "com.nethergrim.bashorg.web.extra.QUOTE_ID";
    public static final String URI_QUOTE = "content://com.nethergrim.bashorg.web.uri.QUOTE";
    public static final String URL_BASHORG_PAGE = "http://bash.im/index/";
    public static final String ROBOTO_LIGHT = "Roboto-Light.ttf";
    public static final String ROBOTO_THIN = "Roboto-Thin.ttf";
    public static final String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final int ALARM_REPEATING_TIMER = 1000 * 60 * 60 * 4;
    public static final int ANIMATION_DURATION = 200;
    public static final Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();
    public static final String EXTRA_LIMIT = "com.nethergrim.bashorg.web.extra.LIMIT";
    public static final String EXTRA_QUOTE_SELECTION = "com.nethergrim.bashorg.web.extra.QUOTE_SELECTION";
    public static final String LINK_TO_PLAY_MARKET = "https://play.google.com/store/apps/details?id=com.nethergrim.bashorg";
    public static float density;
}
