package com.nethergrim.bashorg.fragment;

import android.database.Cursor;
import android.support.v4.content.Loader;

import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 09.01.15 20:58.
 */
public class LikedQuotesFragment extends ViewPagerFragment {


    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.LIKED;
    }

    @Override
    protected void onRefreshTriggered() {
        loadData(getDefaultPageSize());
    }

    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        stopRefreshing();
    }

    @Override
    protected int getDefaultPageSize() {
        return 1000;
    }

    @Override
    protected void resumed() {
        refreshLayout.setEnabled(false);
    }

    @Override
    protected boolean autoLoadNextPage() {
        return true;
    }

}