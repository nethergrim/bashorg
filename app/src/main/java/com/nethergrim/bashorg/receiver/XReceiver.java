package com.nethergrim.bashorg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.web.RunnerService;

/**
 * @author andrey_drobyazko on 23.01.15 19:26.
 */
public class XReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            chargingConnected(true);
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            chargingConnected(false);
        }
    }

    private void chargingConnected(boolean charging) {
        Prefs.setCharging(charging);
        checkPossibilityToFetchData();
    }

    private void checkPossibilityToFetchData() {
        if (Prefs.isCharging() && App.isOnline()) {
            RunnerService.triggerFetching();
        }
    }


}
