package com.nethergrim.bashorg;

import android.app.Application;

import com.nethergrim.bashorg.db.DB;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class App extends Application {

    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Prefs.init(this.getApplicationContext());
        DB.init(this.getApplicationContext());
        Constants.density = getResources().getDisplayMetrics().density;
    }


}
