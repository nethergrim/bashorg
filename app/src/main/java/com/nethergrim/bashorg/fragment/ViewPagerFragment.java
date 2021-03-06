package com.nethergrim.bashorg.fragment;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.QuotesLoader;
import com.nethergrim.bashorg.model.QuoteSelection;

import java.util.Random;

/**
 * Created by nethergrim on 13.01.2015.
 */
public abstract class ViewPagerFragment extends AbstractFragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {

    protected ListView list;
    protected SwipeRefreshLayout refreshLayout;
    protected int loadedItemsCount = 0;
    private int loaderId;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private CursorAdapter adapter;
    private int pageSize = 0;
    private int maxLoadedItem = 0;
    private QuoteSelection quoteSelection;

    public int getLoadedItemsCount() {
        return loadedItemsCount;
    }

    protected abstract QuoteSelection getQuoteSelection();

    protected abstract void onRefreshTriggered();

    protected abstract void onLoaded(Loader<Cursor> loader, Cursor cursor);

    protected abstract int getDefaultPageSize();

    protected abstract void resumed();

    protected abstract boolean autoLoadNextPage();

    protected abstract boolean autoUpdate();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pageSize = getDefaultPageSize();
        if (pageSize < 5) pageSize = 5;
        this.quoteSelection = getQuoteSelection();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_quotes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.myColorActionBar, typedValue, true);
        int mainColor = typedValue.data;

        this.loaderId = new Random().nextInt();
        this.list = (ListView) view.findViewById(R.id.recycler_view);
        this.refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(mainColor);

        this.adapter = new QuoteCursorAdapter(getActivity());
        list.setAdapter(adapter);
        list.setOnScrollListener(this);
        loadData(getDefaultPageSize());
    }

    @Override
    public void onRefresh() {
        maxLoadedItem = 0;
        onRefreshTriggered();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumed();
        if (receiver != null && filter != null) {
            getActivity().registerReceiver(receiver, filter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    protected void loadData(int count) {
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_LIMIT, count);
        args.putSerializable(Constants.EXTRA_QUOTE_SELECTION, quoteSelection);
        getLoaderManager().restartLoader(loaderId, args, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new QuotesLoader(getActivity(), args, autoUpdate());
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) loadedItemsCount = data.getCount();
        Cursor oldCursor = adapter.swapCursor(data);
        if (oldCursor != null)
            oldCursor.close();
        onLoaded(loader, data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        loadedItemsCount = 0;
        Cursor oldCursor = adapter.swapCursor(null);
        if (oldCursor != null)
            oldCursor.close();
        onLoaded(loader, null);
    }

    protected void setBroadcastReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        this.receiver = receiver;
        this.filter = filter;
    }

    protected void stopRefreshing() {
        if (refreshLayout != null) refreshLayout.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (autoLoadNextPage()) {
            int lastVisibleItem = firstVisibleItem + visibleItemCount;
            if (maxLoadedItem < lastVisibleItem) {
                maxLoadedItem = lastVisibleItem;
                if (maxLoadedItem + 5 >= loadedItemsCount) {
                    loadData(loadedItemsCount + pageSize);
                }
            }
        }
    }
}
