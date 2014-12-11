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

    public RunnerService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", ":::" + "started service");
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if (isWiFi){
                loadLastQuotes();
            } else {
                Prefs.setCounnectionCouner(Prefs.getConnectionCounter() + 1);
                MyIntentService.getPageAndSaveQuotes(this, 100000);
            }
        }
        return START_STICKY;
    }


    private void loadLastQuotes(){
        MyIntentService.getPageAndSaveQuotes(this, 100000);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int loadedPage = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 0);
                if (loadedPage > 0)
                    MyIntentService.getPageAndSaveQuotes(RunnerService.this, loadedPage - 1);
                if (loadedPage == 1){
                    unregisterReceiver(receiver);
                }
            }
        };
        registerReceiver(receiver, fetchPageFilter);
    }
}
