package com.nethergrim.bashorg.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.loaders.AbstractLoader;

import java.util.Random;

/**
 * Created by nethergrim on 13.01.2015.
 */
public abstract class ViewPagerFragment extends AbstractFragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    protected ListView list;
    protected SwipeRefreshLayout refreshLayout;
    protected int loaderId;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private CursorAdapter adapter;
    private int pageSize = 0;
    private int loadedItemsCount = 0;

    protected abstract CursorAdapter getAdapter(Context context);

    protected abstract AbstractLoader getLoader(Context context, Bundle args);

    protected abstract void onRefreshTriggered();

    protected abstract void onLoaded(Loader<Cursor> loader, Cursor cursor);

    protected abstract int getDefaultPageSize();

    protected abstract void resumed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pageSize = getDefaultPageSize();
        if (pageSize < 10) pageSize = 15;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_quotes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loaderId = new Random().nextInt();
        this.list = (ListView) view.findViewById(R.id.recycler_view);
        this.refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.main_color, R.color.accent, R.color.main_color, R.color.accent);
        this.adapter = getAdapter(getActivity());
        list.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        onRefreshTriggered();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
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

    protected void loadData() {
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_LIMIT, pageSize);
        getLoaderManager().restartLoader(loaderId, args, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return getLoader(getActivity(), args);
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

    protected void stopRefreshing(){
        if (refreshLayout != null) refreshLayout.setRefreshing(false);
    }

}
