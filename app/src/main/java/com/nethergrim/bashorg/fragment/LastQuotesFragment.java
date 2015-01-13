package com.nethergrim.bashorg.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.widget.CursorAdapter;

import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.QuotesLoader;
import com.nethergrim.bashorg.loaders.LastQuotesCursorLoader;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by nethergrim on 01.12.2014.
 */
public class LastQuotesFragment extends ViewPagerFragment{

    @Override
    protected CursorAdapter getAdapter(Context context) {
        return new QuoteCursorAdapter(context);
    }

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.LAST;
    }

    @Override
    protected void onRefreshTriggered() {
        loadData();
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

    }
}
