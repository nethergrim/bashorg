package com.nethergrim.bashorg.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.LastQuotesCursorLoader;
import com.nethergrim.bashorg.views.MyScrollListener;
import com.nethergrim.bashorg.web.MyIntentService;

/**
 * Created by nethergrim on 01.12.2014.
 */
public class LastQuotesFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private QuoteCursorAdapter adapter;
    private static final int LOADER_CODE = 117;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_quotes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.main_color, R.color.accent, R.color.main_color, R.color.accent);
        ListView listView = (ListView) view.findViewById(R.id.recycler_view);
        adapter = new QuoteCursorAdapter(getActivity(), null);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new MyScrollListener(onTopBarHeightListener));
        loadData();
    }


    private void loadData() {
        if (getLoaderManager().getLoader(LOADER_CODE) == null) {
            getLoaderManager().initLoader(LOADER_CODE, null, this);
        }
        getLoaderManager().getLoader(LOADER_CODE).forceLoad();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new LastQuotesCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
