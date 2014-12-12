package com.nethergrim.bashorg.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.loaders.BestQuotesCursorLoader;

/**
 * Created by andrey_drobyazko on 11.12.14 19:42.
 */
public class BestQuotesFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private QuoteCursorAdapter adapter;
    private static final int LOADER_CODE = 119;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_quotes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(R.id.recycler_view);
        adapter = new QuoteCursorAdapter(getActivity(), null);
        listView.setAdapter(adapter);
        loadData();
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
}
