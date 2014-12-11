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

        Intent intent = new Intent(this, RunnerService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), Constants.ALARM_REPEATING_TIMER, pendingIntent);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }


}
