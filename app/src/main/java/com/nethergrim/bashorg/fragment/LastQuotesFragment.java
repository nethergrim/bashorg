package com.nethergrim.bashorg.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.ListViewAdapter;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.row.QuoteRow;

import io.realm.RealmResults;

/**
 * Created by nethergrim on 01.12.2014.
 */
public class LastQuotesFragment extends AbstractFragment implements View.OnClickListener {

    private ListViewAdapter<QuoteRow> adapter;

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
        for (Quote result : results) {
            adapter.addRow(new QuoteRow(result, this));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
