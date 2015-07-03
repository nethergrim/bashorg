package com.nethergrim.bashorg.web;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.utils.FileUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_FETCH_ALL_DATA)) {
                fetchAllData();
            } else {
                fetchLastData();
            }
        }
        return START_REDELIVER_INTENT;
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
        if (DB.getInstance().getCountOfLoadedQuotes() > 51400) {
            compressAllDbToJson();
        } else {
            fetchAllContent();
        }
    }

    private void fetchAllContent() {
        long lastPage = Prefs.getLastPageNumber();
        ExecutorService es = Executors.newFixedThreadPool(20);
        for (int i = 0; i < lastPage; i++) {
            final int finalI = i;
            es.submit(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "fetching page: " + BashorgParser.getPage(finalI));
                }
            });
        }
    }

    private void compressAllDbToJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = DB.getInstance().compressDbToJson();
                long start = System.currentTimeMillis();
                FileUtils.writeStringAsFile(json, "ХУЙ.json");
                Log.e("TAG", "wrote file to disk: " + String.valueOf(System.currentTimeMillis() - start));
            }
        }).start();
    }


}
