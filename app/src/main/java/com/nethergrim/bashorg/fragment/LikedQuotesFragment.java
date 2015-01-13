package com.nethergrim.bashorg.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.widget.CursorAdapter;

import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.QuotesLoader;
import com.nethergrim.bashorg.loaders.LikedQuotesLoader;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 09.01.15 20:58.
 */
public class LikedQuotesFragment extends ViewPagerFragment {


    @Override
    protected CursorAdapter getAdapter(Context context) {
        return new QuoteCursorAdapter(context);
    }

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.LIKED;
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
        return 15;
    }

    @Override
    protected void resumed() {

    }

}