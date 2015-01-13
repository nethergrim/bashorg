package com.nethergrim.bashorg.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.RandomQuotesCursorLoader;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 11.12.14 19:29.
 */
public class RandomQuotesFragment extends ViewPagerFragment /*extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener */{
    @Override
    protected CursorAdapter getAdapter(Context context) {
        return new QuoteCursorAdapter(context);
    }

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.RANDOM;
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
