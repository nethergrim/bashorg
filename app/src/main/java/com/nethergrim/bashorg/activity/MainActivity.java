package com.nethergrim.bashorg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteAdapter;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.web.MyIntentService;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private Realm realm;
    private RecyclerView recyclerView;
    private QuoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        downloadData(1, 3);
        fetchData();
    }

    private void fetchData() {
        RealmResults<Quote> results = realm.where(Quote.class).findAll().sort("id", false);
        adapter = new QuoteAdapter(results);
        recyclerView.setAdapter(adapter);
    }

    private void downloadData(int start, int to) {
        for (int i = start; i < to; i++) {
            MyIntentService.getPageAndSaveQuotes(this, i);
        }
        fetchData();
    }
}
