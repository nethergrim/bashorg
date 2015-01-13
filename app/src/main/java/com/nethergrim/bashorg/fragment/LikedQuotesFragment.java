package com.nethergrim.bashorg.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.CursorAdapter;

import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.LikedQuotesLoader;

/**
 * Created by andrey_drobyazko on 09.01.15 20:58.
 */
public class LikedQuotesFragment extends ViewPagerFragment {


    @Override
    protected CursorAdapter getAdapter(Context context) {
        return new QuoteCursorAdapter(context);
    }

    @Override
    protected void refresh() {
        loadData();
    }

    @Override
    protected CursorLoader getLoader(Context context, Bundle args) {
        return new LikedQuotesLoader(context);
    }


    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        stopRefreshing();
    }

}