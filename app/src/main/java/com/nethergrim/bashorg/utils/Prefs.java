package com.nethergrim.bashorg.utils;

import com.nethergrim.bashorg.App;

import ru.noties.simpleprefs.SimplePref;

/**
 * @author andrej on 25.08.15.
 */
public class Prefs {

    public static final String KEY_FIRST_LAUNCH = "fl";
    public static final String KEY_LAUNCH_TIME = "lt";
    public static final long MIN_DELAY_FOR_FIRST_LAUNCH_MS = 1000 * 60; // 60 sec
    private static SimplePref pref;

    public static void setFirstAppLaunch(boolean firstAppLaunch) {
        init();
        pref.set(KEY_FIRST_LAUNCH, firstAppLaunch);
    }

    public static boolean isFirstAppLauch() {
        init();
        return pref.get(KEY_FIRST_LAUNCH, true);
    }

    public static void writeLaunchTime() {
        init();
        long lastLaunchTime = getLaunchTime();
        if (lastLaunchTime == -1) { // really first launch
            pref.set(KEY_LAUNCH_TIME, System.currentTimeMillis());
        } else {
            // nothing here
        }
    }

    public static long getLaunchTime() {
        init();
        return pref.get(KEY_LAUNCH_TIME, -1l);
    }

    private static void init() {
        pref = new SimplePref(App.getInstance(), "simple_prefs");
    }

}
