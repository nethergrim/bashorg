package com.nethergrim.bashorg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.ListViewAdapter;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.row.QuoteRow;
import com.nethergrim.bashorg.row.Row;
import com.nethergrim.bashorg.web.MyIntentService;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private Realm realm;
    private ListViewAdapter<Row> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(this);
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new ListViewAdapter<Row>(this);
        listView.setAdapter(adapter);
        downloadData(1, 3);
        fetchData();
    }

    private void fetchData() {
        RealmResults<Quote> results = realm.where(Quote.class).findAll().sort("id", false);
        adapter.clearAdapter();
        for (Quote result : results) {
            adapter.addRow(new QuoteRow(result));
        }
        adapter.notifyDataSetChanged();
    }

    private void downloadData(int start, int to) {
        for (int i = start; i < to; i++) {
            MyIntentService.getPageAndSaveQuotes(this, i);
        }
    }
}
