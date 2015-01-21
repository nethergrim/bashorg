package com.nethergrim.bashorg.fragment;

import android.database.Cursor;
import android.support.v4.content.Loader;

import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 11.12.14 19:29.
 */
public class RandomQuotesFragment extends ViewPagerFragment {

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.RANDOM;
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
        return 50;
    }

    @Override
    protected void resumed() {

    }

    @Override
    protected boolean autoLoadNextPage() {
        return false;
    }
}
