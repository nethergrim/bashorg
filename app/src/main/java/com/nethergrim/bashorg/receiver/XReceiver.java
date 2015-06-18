package com.nethergrim.bashorg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.web.RunnerService;

/**
 * @author andrey_drobyazko on 23.01.15 19:26.
 */
public class XReceiver extends BroadcastReceiver {


    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            chargingConnected(true);
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            chargingConnected(false);
        }
        checkPossibilityToFetchData();
    }

    private void chargingConnected(boolean charging) {
        Prefs.setCharging(charging);
    }

    private void checkPossibilityToFetchData() {
        if (Prefs.isCharging() && getConnectivityStatus(App.getInstance()) == TYPE_WIFI) {
            RunnerService.triggerFetching();
        }
    }
}
