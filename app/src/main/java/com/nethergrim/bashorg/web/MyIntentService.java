package com.nethergrim.bashorg.web;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static final String ACTION_FETCH_PAGE = "com.nethergrim.bashorg.web.action.FETCH_PAGE";
    private static final String EXTRA_PAGE_NUMBER = "com.nethergrim.bashorg.web.extra.PARAM1";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void getPageAndSaveQuotes(Context context, int pageNumber) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FETCH_PAGE);
        intent.putExtra(EXTRA_PAGE_NUMBER, pageNumber);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_PAGE.equals(action)) {
                final int param1 = intent.getIntExtra(EXTRA_PAGE_NUMBER, 10000000);
                handleActionFetchPage(param1);
            }
        }
    }


    private void handleActionFetchPage(int pageNumber) {
        int result = BashorgParser.parsePage(String.valueOf(pageNumber), getApplicationContext());
        if (result > 0) {
            Log.e("log", "parsed page: " + result);
        } else {
            Log.e("log", "error on parsing " + pageNumber);
        }
    }

}
