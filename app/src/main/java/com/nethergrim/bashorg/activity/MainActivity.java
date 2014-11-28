package com.nethergrim.bashorg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.web.MyIntentService;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private ListView listView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(this);
        listView = (ListView) findViewById(R.id.list);
        getQuotes();
        fetchData();
    }

    public RealmResults<Quote> getQuotes() {
        RealmResults<Quote> result = realm.where(Quote.class).findAll().sort("id", false);
        Log.e("log", "fetched " + result.size() + " quotes");
        return result;
    }

    private void fetchData() {
        MyIntentService.getPageAndSaveQuotes(this, 1000000);
        for (int i = 200; i < 400; i++) {
            MyIntentService.getPageAndSaveQuotes(this, i);
        }
    }
}
