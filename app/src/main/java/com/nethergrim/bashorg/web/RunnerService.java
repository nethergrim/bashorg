package com.nethergrim.bashorg.web;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;

public class RunnerService extends Service {

    private IntentFilter fetchPageFilter = new IntentFilter(Constants.ACTION_FETCH_PAGE);
    private BroadcastReceiver receiver;

    public RunnerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context) {
        // TODO fix later
        context.startService(new Intent(context, RunnerService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", ":::" + "started service");
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if (isWiFi) {
                if (Prefs.isDatabaseFilled()) {
                    loadLastQuotes();
                } else {
                    loadAllQuotes();
                }
            } else {
                loadLastQuotes();
            }
        }
        return START_STICKY;
    }

    private void loadLastQuotes() {
        MyIntentService.getPageAndSaveQuotes(this, 1000000);
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() - 1));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() - 2));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() - 3));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() - 4));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() + 1));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() + 2));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() + 3));
        MyIntentService.getPageAndSaveQuotes(this, (int) (Prefs.getLastPageNumber() + 4));
        stopSelf();
    }

    private void loadAllQuotes() {
        Log.e("TAG", ":::" + "loading all quotes");
        MyIntentService.getPageAndSaveQuotes(this, 1000000);
        MyIntentService.getPageAndSaveQuotes(this, Prefs.getSmallestLoadedPage() - 1);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int loadedPage = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 0);
                if (loadedPage > 0)
                    MyIntentService.getPageAndSaveQuotes(RunnerService.this, Prefs.getSmallestLoadedPage() - 1);
                if (loadedPage == 1) {
                    unregisterReceiver(receiver);
                    Prefs.setDatabaseFilled(true);
                    Log.e("TAG", "DB IS FILLED!");
                    stopSelf();
                }
            }
        };
        registerReceiver(receiver, fetchPageFilter);
    }
}
