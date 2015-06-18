package com.nethergrim.bashorg.web;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;

public class RunnerService extends Service {

    public static void triggerFetching() {
        start(App.getInstance().getApplicationContext());
    }

    public static void start(Context context) {
        context.startService(new Intent(context, RunnerService.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fetchLastData();
        return START_STICKY;
    }

    private void fetchLastData() {
        MyIntentService.getLastPage();
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() - 1));
        for (int i = 0; i < 30; i++) {
            MyIntentService.getRandomPage();
        }
        stopSelf();
    }


}
