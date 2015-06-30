package com.nethergrim.bashorg.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.viewholder.QuoteViewHolder;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.utils.ThemeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 27.06.15.
 */
public class FontSettingsController extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

    public static final int MAX_VALUE = 22;
    public static final int MIN_VALUE = 10;

    @InjectView(R.id.seekBar)
    SeekBar mSeekbar;
    @InjectView(R.id.container_for_example_quote)
    FrameLayout mQuoteContainer;

    private QuoteViewHolder mQuoteVH;

    public FontSettingsController(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(context);
        }
    }

    public FontSettingsController(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context);
        }
    }

    public FontSettingsController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context) {
        View root = inflate(context, R.layout.controller_font_settings, null);

        ButterKnife.inject(this, root);
        int fontSizeSP = ThemeUtils.getFontSize();
        mSeekbar.setProgress(fontSizeSP - MIN_VALUE);
        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.setMax(MAX_VALUE);
        View v = LayoutInflater.from(context).inflate(R.layout.row_quote, mQuoteContainer, false);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(v);
        v.setTag(quoteViewHolder);

        mQuoteVH = new QuoteViewHolder(v);
        Quote quote = ThemeUtils.getDefaultQuote();
        mQuoteVH.bindQuouteData(quote, context);
        mQuoteContainer.removeAllViews();
        mQuoteContainer.addView(v);
        mQuoteVH.changeTextSize(fontSizeSP);
        addView(root);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progress += MIN_VALUE;
        if (fromUser) {
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
