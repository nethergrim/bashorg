package com.nethergrim.bashorg.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.Loader;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.model.QuoteSelection;
import com.nethergrim.bashorg.web.MyIntentService;

/**
 * Created by andrey_drobyazko on 11.12.14 19:42.
 */
public class BestQuotesFragment extends ViewPagerFragment {

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.BEST;
    }

    @Override
    protected void onRefreshTriggered() {
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 1);
        loadData(getDefaultPageSize());
    }

    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            MyIntentService.getTopPageAndSaveQuotes(getActivity(), 1);
        } else {
            int nextPage =  (cursor.getCount() / getDefaultPageSize() )+ 1;
            MyIntentService.getTopPageAndSaveQuotes(getActivity(), nextPage);
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
                if (pageNumber == 1){
                    loadData(getDefaultPageSize());
                }
            }
        }, new IntentFilter(Constants.ACTION_FETCH_TOP_PAGE));
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
