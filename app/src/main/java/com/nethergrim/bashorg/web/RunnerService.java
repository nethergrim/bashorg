package com.nethergrim.bashorg.web;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.spice.SpiceFetchPageRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class RunnerService extends Service {

    public static final String ACTION_FETCH_LAST_DATA = "fetch_last_data";
    public static final String ACTION_FETCH_ALL_DATA = "fetch_all_data";
    /***
     * With {@link UncachedSpiceService} there is no cache management.
     * Remember to declare it in AndroidManifest.xml
     */
    private SpiceManager spiceManager = new SpiceManager(
            UncachedSpiceService.class);


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
        spiceManager.start(this);
        final int lastPageNumber = (int) Prefs.getLastPageNumber();
        final int id = 0;


        final NotificationManager mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getString(R.string.quotes_fetching))
                .setContentText(getString(R.string.quotes_fetching))
                .setSmallIcon(android.R.drawable.ic_menu_rotate);

        // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr += 5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(id, mBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5 * 1000);
                            } catch (InterruptedException e) {
                                Log.d("TAG", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(id, mBuilder.build());
                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();

        mBuilder.setProgress(lastPageNumber, 0, false);
        mNotifyManager.notify(id, mBuilder.build());

        final int[] currentProgress = {0};

        for (int i = lastPageNumber; i >= 0; i--) {
            SpiceFetchPageRequest request = new SpiceFetchPageRequest(i);
            spiceManager.execute(request, new RequestListener<Integer>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    Log.e("TAG", "request failed: " + spiceException.getMessage());
                }

                @Override
                public void onRequestSuccess(Integer integer) {
                    Log.e("TAG", "success: " + integer);
                    currentProgress[0] = currentProgress[0] + 1;
                    mBuilder.setProgress(lastPageNumber, currentProgress[0], false);
                    mNotifyManager.notify(id, mBuilder.build());
                }
            });
        }


//        Log.e("TAG", "fetching pages from " + lastPageNumber + " to 0");
//        final PаgeFetchedBroadcastReceiver receiver = new PаgeFetchedBroadcastReceiver(this, new PаgeFetchedBroadcastReceiver.PageLoadedInterface() {
//            @Override
//            public void onPageLoaded(int pageNumber) {
//                Log.e("TAG", "page loaded: " + pageNumber);
//                if (pageNumber == 1) {
//                    Log.e("TAG", "stopping self");
////                    receiver.unregister(RunnerService.this);
//                    stopSelf();
//                }
//            }
//        });
//        for (int i = lastPageNumber; i >= 0; --i) {
//            MyIntentService.getPageAndSaveQuotes(this, i);
//        }

    }


}
