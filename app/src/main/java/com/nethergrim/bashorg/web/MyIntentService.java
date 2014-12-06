package com.nethergrim.bashorg.web;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nethergrim.bashorg.Constants;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    public static void getPageAndSaveQuotes(Context context, int pageNumber) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(Constants.ACTION_FETCH_PAGE);
        intent.putExtra(Constants.EXTRA_PAGE_NUMBER, pageNumber);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.ACTION_FETCH_PAGE.equals(action)) {
                final int param1 = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 10000000);
                handleActionFetchPage(param1);
            }
        }
    }


    private void handleActionFetchPage(int pageNumber) {
        int result = BashorgParser.parsePage(String.valueOf(pageNumber), getApplicationContext());
        if (result > 0) {
            Intent intent = new Intent(Constants.ACTION_FETCH_PAGE);
            intent.putExtra(Constants.EXTRA_PAGE_NUMBER, result);
            sendBroadcast(intent);
            Log.e("log", "parsed page: " + result);
        } else {
            Log.e("log", "error on parsing " + pageNumber);
        }
    }

}
