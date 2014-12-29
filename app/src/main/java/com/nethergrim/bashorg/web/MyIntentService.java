package com.nethergrim.bashorg.web;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.db.DB;

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

    public static void getTopPageAndSaveQuotes(Context context, int pageNumber){
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
                final int param1 = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 10000000);
                handleActionFetchPage(param1);
            } else if (Constants.ACTION_FETCH_TOP_PAGE.equals(action)){
                handeActionFetchTopTage(intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 1));
            }
        }
    }
    private void handeActionFetchTopTage(int pageNumber){
        Log.e("TAG", "fetching top page: " + pageNumber);
        int pn = BashorgParser.parseTopTage(pageNumber);
        Prefs.setMaxTopPage(pn);
        Intent intent = new Intent(Constants.ACTION_FETCH_TOP_PAGE);
        intent.putExtra(Constants.EXTRA_PAGE_NUMBER, pn);
        sendBroadcast(intent);
        Log.e("TAG", "loaded top page: " + pn);
    }

    private void handleActionFetchPage(int pageNumber) {
        DB db = DB.getInstance();
        if (pageNumber == 0) return;
        if (!db.isPageSaved(String.valueOf(pageNumber))){
            int result = BashorgParser.parsePage(String.valueOf(pageNumber));
            if (result > 0) {
                Prefs.setSmallestLoadedPage(result);
                Intent intent = new Intent(Constants.ACTION_FETCH_PAGE);
                intent.putExtra(Constants.EXTRA_PAGE_NUMBER, result);
                sendBroadcast(intent);
                Log.e("PARSE", "loaded and parsed page: " + result);
            }
        } else {
            if (pageNumber != Prefs.getSmallestLoadedPage()){
                handleActionFetchPage(Prefs.getSmallestLoadedPage() - 1);
            } else {
                handleActionFetchPage(Prefs.getSmallestLoadedPage() - 2);
            }

        }

    }

}
