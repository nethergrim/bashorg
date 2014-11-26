package com.nethergrim.bashorg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteAdapter;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.web.BashorgParser;

import io.realm.Realm;

public class MainActivity extends Activity {

    private ListView listView;
    private QuoteAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(this);
        listView = (ListView) findViewById(R.id.list);
        adapter = new QuoteAdapter(this, realm.where(Quote.class).findAll().sort("id", false), true);
        listView.setAdapter(adapter);
        BashorgParser.getAndParse(10000000, null, this);
        BashorgParser.getAndParse(6, null, this);

    }
}
