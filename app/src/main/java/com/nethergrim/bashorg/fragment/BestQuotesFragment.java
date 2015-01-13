package com.nethergrim.bashorg.fragment;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.widget.CursorAdapter;

import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 11.12.14 19:42.
 */
public class BestQuotesFragment extends ViewPagerFragment {

    @Override
    protected CursorAdapter getAdapter(Context context) {
        return new QuoteCursorAdapter(context);
    }

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.BEST;
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
