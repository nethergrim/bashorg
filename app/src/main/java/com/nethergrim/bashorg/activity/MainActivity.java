package com.nethergrim.bashorg.activity;

import android.app.Activity;
import android.os.Bundle;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.web.BashorgParser;
import com.nethergrim.bashorg.web.Client;
import com.nethergrim.bashorg.web.WebStringCallback;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client.getPage(1, new WebStringCallback() {
            @Override
            public void callback(String result, boolean ok) {
                if (ok){
                    BashorgParser.parseNewPage(result);
                }
            }
        });
    }
}
