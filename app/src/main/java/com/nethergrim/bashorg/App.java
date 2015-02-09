package com.nethergrim.bashorg;

import android.app.Application;

import com.nethergrim.bashorg.db.DB;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class App extends Application {

    private static App mInstance;
    private OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Prefs.init(this.getApplicationContext());
        DB.init(this.getApplicationContext());
        Constants.density = getResources().getDisplayMetrics().density;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }


}
