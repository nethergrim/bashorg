package com.nethergrim.bashorg.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.BestQuotesCursorLoader;
import com.nethergrim.bashorg.web.MyIntentService;

/**
 * Created by andrey_drobyazko on 11.12.14 19:42.
 */
public class BestQuotesFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private QuoteCursorAdapter adapter;
    private static final int LOADER_CODE = 119;
    private SwipeRefreshLayout refreshLayout;
    private boolean loading = false;

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
        listView.setOnScrollListener(this);
        loadData();
        IntentFilter filter = new IntentFilter(Constants.ACTION_FETCH_TOP_PAGE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (loading && intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 1) > 1){
                    loading = false;
                }
            }
        };
        getActivity().registerReceiver(receiver, filter);
    }

    private void loadData(){
        if (getLoaderManager().getLoader(LOADER_CODE) == null){
            getLoaderManager().initLoader(LOADER_CODE, null, this);
        }
        getLoaderManager().getLoader(LOADER_CODE).forceLoad();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new BestQuotesCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onRefresh() {
        MyIntentService.getTopPageAndSaveQuotes(getActivity(), 1);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisiblePage = firstVisibleItem + visibleItemCount;
        if (lastVisiblePage  + 5 == Prefs.getMaxTopPage() * 50){
            loading = true;
            MyIntentService.getTopPageAndSaveQuotes(getActivity(), Prefs.getMaxTopPage() + 1);
        }
    }
}
