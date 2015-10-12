package com.nethergrim.bashorg.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.Loader;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.model.QuoteSelection;
import com.nethergrim.bashorg.web.MyIntentService;

/**
 * Created by nethergrim on 01.12.2014.
 */
public class LastQuotesFragment extends ViewPagerFragment{

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.LAST;
    }

    @Override
    protected void onRefreshTriggered() {
        MyIntentService.getRandomPage();
        MyIntentService.getLastPage();
        loadData(getDefaultPageSize());
    }

    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            MyIntentService.getLastPage();
        } else {
            int nextPage = (int) (Prefs.getLastPageNumber() - ( cursor.getCount() / getDefaultPageSize() ));
            MyIntentService.getPageAndSaveQuotes(getActivity(), nextPage);
        }
    }

    @Override
    protected int getDefaultPageSize() {
        return Constants.DEFAULT_PAGE_SIZE;
    }

    @Override
    protected void resumed() {
        setBroadcastReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pageNumber = intent.getExtras().getInt(Constants.EXTRA_PAGE_NUMBER);
                stopRefreshing();
                if (pageNumber == Prefs.getLastPageNumber()){
                    loadData(getDefaultPageSize());
                }
            }
        }, new IntentFilter(Constants.ACTION_FETCH_PAGE));
    }

    @Override
    protected boolean autoLoadNextPage() {
        return true;
    }

    @Override
    protected boolean autoUpdate() {
        return true;
    }
}
