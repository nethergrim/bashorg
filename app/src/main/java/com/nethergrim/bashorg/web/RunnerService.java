package com.nethergrim.bashorg.web;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;

public class RunnerService extends Service {

    private static boolean fetching = false;

    public static void triggerFetching(){
        if (!fetching && Prefs.isCharging() && Prefs.isConnectedToWifi()){
//            fetching = true;
            Log.e("TAG","fetching");
            start(App.getInstance().getApplicationContext());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context) {
        context.startService(new Intent(context, RunnerService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fetchAllDB();
        return START_STICKY;
    }

    private void fetchAllDB(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int maxPage = (int) Prefs.getLastPageNumber();
                Log.e("TAG","-------------------------------------\nFETCHING ALL THE THINGS!: " + maxPage);
                QuouteBuffer buffer = new QuouteBuffer();
                new Thread(new QuotesReceiver(buffer)).start();
                for (int i = 0; i < maxPage; i++) {
                    new Thread(new QuotesProvider(i, buffer)).start();
                }
            }
        }).start();
    }

}
