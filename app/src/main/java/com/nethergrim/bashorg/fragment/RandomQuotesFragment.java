package com.nethergrim.bashorg.fragment;

import android.database.Cursor;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 11.12.14 19:29.
 */
public class RandomQuotesFragment extends ViewPagerFragment implements View.OnClickListener {

    @Override
    protected QuoteSelection getQuoteSelection() {
        return QuoteSelection.RANDOM;
    }

    @Override
    protected void onRefreshTriggered() {
        loadData(getDefaultPageSize());
    }

    @Override
    protected void onLoaded(Loader<Cursor> loader, Cursor cursor) {
        stopRefreshing();
    }

    @Override
    protected int getDefaultPageSize() {
        return 50;
    }

    @Override
    protected void resumed() {
        if (list.getFooterViewsCount() == 0){
            View footer = LayoutInflater.from(getActivity()).inflate(R.layout.footer_load_more, null);
            list.addFooterView(footer);
            footer.findViewById(R.id.btn_load_more).setOnClickListener(this);
        }
    }

    @Override
    protected boolean autoLoadNextPage() {
        return false;
    }

    @Override
    public void onClick(View v) {
        loadData(getDefaultPageSize());
    }
}
