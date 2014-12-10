package com.nethergrim.bashorg.web;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;

import java.util.List;

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
        if (!Prefs.isPageLoaded(pageNumber)){
            int result = BashorgParser.parsePage(String.valueOf(pageNumber));
            if (result > 0) {
                List<String> pages = Prefs.getPageIds();
                pages.add(String.valueOf(result));
                Prefs.setSavedPages(pages);
                Intent intent = new Intent(Constants.ACTION_FETCH_PAGE);
                intent.putExtra(Constants.EXTRA_PAGE_NUMBER, result);
                sendBroadcast(intent);
                Log.e("log", "loaded and parsed page: " + result);
            } else {
                Log.e("log", "error on parsing " + pageNumber);
            }
        } else {
            Log.e("TAG",":::" + "page is loaded already: " + pageNumber);
            Intent intent = new Intent(Constants.ACTION_FETCH_PAGE);
            intent.putExtra(Constants.EXTRA_PAGE_NUMBER, pageNumber);
            sendBroadcast(intent);
        }

    }

}
