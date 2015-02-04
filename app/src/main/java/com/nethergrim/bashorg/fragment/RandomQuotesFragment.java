package com.nethergrim.bashorg.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by andrey_drobyazko on 11.12.14 19:29.
 */
public class RandomQuotesFragment extends ViewPagerFragment implements View.OnClickListener {

    boolean emptyScreen = false;

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
        emptyScreen = cursor == null || cursor.getCount() == 0;
        stopRefreshing();
    }

    @Override
    protected int getDefaultPageSize() {
        return Constants.DEFAULT_PAGE_SIZE;
    }

    @Override
    protected void resumed() {
        if (list.getFooterViewsCount() == 0){
            View footer = LayoutInflater.from(getActivity()).inflate(R.layout.footer_load_more, null);
            list.addFooterView(footer);
            footer.findViewById(R.id.btn_load_more).setOnClickListener(this);
        }
        setBroadcastReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (emptyScreen){
                    loadData(getDefaultPageSize());
                }
            }
        }, new IntentFilter(Constants.ACTION_FETCH_PAGE));
    }

    @Override
    protected boolean autoLoadNextPage() {
        return false;
    }

    @Override
    public void onClick(View v) {
        list.setSelection(0);
        onRefreshTriggered();
    }
}
