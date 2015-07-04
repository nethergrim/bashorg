package com.nethergrim.bashorg;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.purchases.InAppBillingServiceHolder;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author nethergrim on 26.11.2014.
 */
public class App extends Application {

    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static boolean isOnline() {
        return getInstance().isOnline_();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Prefs.init(this.getApplicationContext());
        DB.init(this.getApplicationContext());
        Constants.density = getResources().getDisplayMetrics().density;
        InAppBillingServiceHolder.bindToService();
        initRealm();
    }

    private void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .name("realm_db")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(realmConfig);
    }

    public boolean isOnline_() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }


}
