package com.nethergrim.bashorg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.ThemeUtils;
import com.nethergrim.bashorg.adapter.QuoteCursorAdapter;
import com.nethergrim.bashorg.adapter.viewholder.QuoteViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 20.06.15.
 */
public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final int MAX_VALUE = 24;
    public static final int MIN_VALUE = 10;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.seekBar)
    SeekBar mSeekbar;
    @InjectView(R.id.tv_progress_value)
    TextView mTvProgressValue;
    @InjectView(R.id.container_for_example_quote)
    FrameLayout mQuoteContainer;

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
        initSeekBar();
    }

    private void initSeekBar() {
        int fontSizeSP = ThemeUtils.getFontSize();
        mTvProgressValue.setText(String.valueOf(fontSizeSP));
        mSeekbar.setProgress(fontSizeSP);
        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.setMax(MAX_VALUE);
        QuoteCursorAdapter adapter = new QuoteCursorAdapter(this);
        View view = adapter.getView(0, null, mQuoteContainer);
        QuoteViewHolder vh = new QuoteViewHolder(view);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progress < MIN_VALUE) {
            seekBar.setProgress(MIN_VALUE);
            progress = MIN_VALUE;
        }
        if (fromUser) {
            mTvProgressValue.setText(String.valueOf(progress));
            ThemeUtils.setFontSize(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
