package com.nethergrim.bashorg;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.web.RunnerService;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Prefs.init(this.getApplicationContext());
        DB.init(this.getApplicationContext());
        Constants.density = getResources().getDisplayMetrics().density;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }


}
