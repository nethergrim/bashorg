package com.nethergrim.bashorg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.viewholder.QuoteViewHolder;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.utils.ThemeUtils;

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
    @InjectView(R.id.layout_theme)
    LinearLayout mThemeLayout;
    @InjectView(R.id.text_theme_value)
    TextView mTVThemeValue;

    private QuoteViewHolder mQuoteVH;

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
        initSeekBar();
        initThemeLayout();
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

    private void initSeekBar() {
        int fontSizeSP = ThemeUtils.getFontSize();
        mTvProgressValue.setText(String.valueOf(fontSizeSP));
        mSeekbar.setProgress(fontSizeSP);
        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.setMax(MAX_VALUE);
        View v = LayoutInflater.from(this).inflate(R.layout.row_quote, mQuoteContainer, false);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(v);
        v.setTag(quoteViewHolder);

        mQuoteVH = new QuoteViewHolder(v);
        Quote quote = ThemeUtils.getDefaultQuote();
        mQuoteVH.bindQuouteData(quote, this);
        mQuoteContainer.removeAllViews();
        mQuoteContainer.addView(v);
        mQuoteVH.changeTextSize(fontSizeSP);

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
            mQuoteVH.changeTextSize(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
