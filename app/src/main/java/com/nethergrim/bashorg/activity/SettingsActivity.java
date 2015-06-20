package com.nethergrim.bashorg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.ThemeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 20.06.15.
 */
public class SettingsActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ThemeUtils.isADarkTheme()) {
            setTheme(R.style.My_Theme_Dark);
        } else {
            setTheme(R.style.My_Theme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        initActionBar();
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
