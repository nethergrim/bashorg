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
        loadData(getDefaultPageSize());
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 1);
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 2);
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 3);
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 4);
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 5);
    }

    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() == 0){
            MyIntentService.getTopPageAndSaveQuotes(getActivity(), 1);
        }
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
                if (pageNumber == 1){
                    loadData(getDefaultPageSize());
                    stopRefreshing();
                }
            }
        }, new IntentFilter(Constants.ACTION_FETCH_TOP_PAGE));
    }

    @Override
    protected boolean autoLoadNextPage() {
        return true;
    }


}
