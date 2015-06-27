package com.nethergrim.bashorg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.utils.ThemeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 20.06.15.
 */
public class SettingsActivity extends AppCompatActivity {


    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.layout_theme)
    LinearLayout mThemeLayout;
    @InjectView(R.id.text_theme_value)
    TextView mTVThemeValue;


    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtils.getCurrentTheme().getStyleResourceId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
        initThemeLayout();
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

    private void initThemeLayout() {
        mTVThemeValue.setText(ThemeUtils.getCurrentTheme().getStringThemeNameResourceId());
        mThemeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeSelectorActivity.start(SettingsActivity.this);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
