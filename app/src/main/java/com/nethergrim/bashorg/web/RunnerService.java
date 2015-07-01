package com.nethergrim.bashorg.web;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.utils.PаgeFetchedBroadcastReceiver;

public class RunnerService extends Service {

    public static final String ACTION_FETCH_LAST_DATA = "fetch_last_data";
    public static final String ACTION_FETCH_ALL_DATA = "fetch_all_data";

    public static void triggerFetching() {
        Context context = App.getInstance().getApplicationContext();
        Intent intent = new Intent(context, RunnerService.class);
        intent.setAction(ACTION_FETCH_LAST_DATA);
        context.startService(intent);
    }

    public static void fetchAllBashorgData() {
        if (Prefs.getSmallestLoadedPage() > 1) {
            Context context = App.getInstance().getApplicationContext();
            Intent intent = new Intent(context, RunnerService.class);
            intent.setAction(ACTION_FETCH_ALL_DATA);
            context.startService(intent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(ACTION_FETCH_ALL_DATA)) {
            fetchAllData();
        } else {
            fetchLastData();
        }

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

    private void fetchAllData() {
        int lastPageNumber = (int) Prefs.getLastPageNumber();
        Log.e("TAG", "fetching pages from " + lastPageNumber + " to 0");
        final PаgeFetchedBroadcastReceiver receiver = new PаgeFetchedBroadcastReceiver(this, new PаgeFetchedBroadcastReceiver.PageLoadedInterface() {
            @Override
            public void onPageLoaded(int pageNumber) {
                Log.e("TAG", "page loaded: " + pageNumber);
                if (pageNumber == 1) {
                    Log.e("TAG", "stopping self");
//                    receiver.unregister(RunnerService.this);
                    stopSelf();
                }
            }
        });
        for (int i = lastPageNumber; i >= 0; --i) {
            MyIntentService.getPageAndSaveQuotes(this, i);
        }

    }


}
