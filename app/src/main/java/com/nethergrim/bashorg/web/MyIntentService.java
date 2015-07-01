package com.nethergrim.bashorg.web;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.db.DB;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void getLastPage() {
        getPageAndSaveQuotes(App.getInstance().getApplicationContext(), Constants.PAGE_MAX);
    }

    public static void getRandomPage() {
        Intent intent = new Intent(App.getInstance().getApplicationContext(), MyIntentService.class);
        intent.setAction(Constants.ACTION_FETCH_RANDOM_PAGE);
        App.getInstance().getApplicationContext().startService(intent);
    }

    public static void getPageAndSaveQuotes(Context context, int pageNumber) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(Constants.ACTION_FETCH_PAGE);
        intent.putExtra(Constants.EXTRA_PAGE_NUMBER, pageNumber);
        context.startService(intent);
    }

    public static void getTopPageAndSaveQuotes(Context context, int pageNumber) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(Constants.ACTION_FETCH_TOP_PAGE);
        intent.putExtra(Constants.EXTRA_PAGE_NUMBER, pageNumber);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.ACTION_FETCH_PAGE.equals(action)) {
                final int param1 = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, Constants.PAGE_MAX);
                handleActionFetchPage(param1);
            } else if (Constants.ACTION_FETCH_TOP_PAGE.equals(action)) {
                handeActionFetchTopTage(intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 1));
            } else if (Constants.ACTION_FETCH_RANDOM_PAGE.equals(action)) {
                handleActionFetchRandomPage();
            }
        }
    }

    private void handleActionFetchRandomPage() {
        BashorgParser.parseRandomPage();
    }

    private void handeActionFetchTopTage(int pageNumber) {
        int pn = BashorgParser.parsePageFromTop(pageNumber);
        Prefs.setMaxTopPage(pn);
        Intent intent = new Intent(Constants.ACTION_FETCH_TOP_PAGE);
        intent.putExtra(Constants.EXTRA_PAGE_NUMBER, pn);
        sendBroadcast(intent);
    }

    private void handleActionFetchPage(int pageNumber) {
        if (pageNumber == 0) {
            return;
        }
        DB db = DB.getInstance();
        if (!db.isPageSaved(String.valueOf(pageNumber))) {
            int page = BashorgParser.parsePage(String.valueOf(pageNumber));
            if (page > 0) {
                Prefs.setSmallestLoadedPage(page);
                Intent intent = new Intent(Constants.ACTION_FETCH_PAGE);
                intent.putExtra(Constants.EXTRA_PAGE_NUMBER, page);
                sendBroadcast(intent);
            }
        }
    }

}
