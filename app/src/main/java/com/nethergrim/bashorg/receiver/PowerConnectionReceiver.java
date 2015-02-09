package com.nethergrim.bashorg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.web.RunnerService;

/**
 * Created by andrey_drobyazko on 23.01.15 19:26.
 */
public class PowerConnectionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = false;

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Log.e("TAG", "connected");
            status = true;
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Log.e("TAG","disconnected");
            status = false;
        }
        Prefs.setCharging(status);
        RunnerService.triggerFetching();
    }
}
