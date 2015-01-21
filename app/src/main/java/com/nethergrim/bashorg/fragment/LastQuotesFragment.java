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
        loadData(getDefaultPageSize());
        MyIntentService.getPageAndSaveQuotes(getActivity(), 10000000);
    }

    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        stopRefreshing();
    }

    @Override
    protected int getDefaultPageSize() {
        return 10;
    }

    @Override
    protected void resumed() {
        setBroadcastReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pageNumber = intent.getExtras().getInt(Constants.EXTRA_PAGE_NUMBER);
                if (pageNumber == Prefs.getLastPageNumber()){
                    loadData(150 + loadedItemsCount);
                }
            }
        }, new IntentFilter(Constants.ACTION_FETCH_PAGE));
    }

    @Override
    protected boolean autoLoadNextPage() {
        return true;
    }
}
