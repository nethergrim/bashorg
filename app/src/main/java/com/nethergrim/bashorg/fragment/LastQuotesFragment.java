package com.nethergrim.bashorg.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.ListViewAdapter;
import com.nethergrim.bashorg.callback.OnQuoteSharePressed;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.row.QuoteRow;
import com.nethergrim.bashorg.web.MyIntentService;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by nethergrim on 01.12.2014.
 */
public class LastQuotesFragment extends AbstractFragment implements OnQuoteSharePressed {

    private ListViewAdapter<QuoteRow> adapter;
    private IntentFilter filter = new IntentFilter(Constants.ACTION_FETCH_PAGE);
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_quotes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(R.id.recycler_view);
        adapter = new ListViewAdapter<QuoteRow>(getActivity());
        listView.setAdapter(adapter);
        RealmResults<Quote> results = realm.where(Quote.class).findAll().sort("id", false);
        addRowsToAdapter(results);
    }

    private void addRowsToAdapter(RealmResults<Quote> results) {
        for (Quote result : results) {
            adapter.addRow(new QuoteRow(result, this));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int loadedPage = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, 0);
                Log.e("LOG", "received broadcast - loaded page # " + loadedPage);
                MyIntentService.getPageAndSaveQuotes(getActivity(), loadedPage - 1);
                RealmResults<Quote> results = realm.where(Quote.class).findAll().sort("id", false);
                if (results.size()  > 50){
                    List<Quote> quoteList = new ArrayList<Quote>();
                    for (int i = 0; i < 50; i++) {
                        quoteList.add(results.get(i));
                    }
                    for (Quote quote : quoteList) {
                        adapter.addRow(new QuoteRow(quote, LastQuotesFragment.this));
                        adapter.notifyDataSetChanged();
                    }
                }



            }
        };
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onQuoteSharePressed(Quote quote) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, quote.getText());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
