package com.nethergrim.bashorg.row;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.QuoteViewHolder;
import com.nethergrim.bashorg.model.Quote;

/**
 * Created by andrey_drobyazko on 02.12.14 20:01.
 */
public class QuoteRow implements Row<QuoteViewHolder>, View.OnClickListener {

    private Quote quote;
    private boolean checked = false;
    private QuoteViewHolder quoteViewHolder;

    public QuoteRow(Quote quote) {
        this.quote = quote;
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_quote, viewGroup, false);
        QuoteViewHolder vh = new QuoteViewHolder(v);
        if (Build.VERSION.SDK_INT >= 21){
            vh.cardView.setCardElevation(8);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder viewHolder) {
        this.quoteViewHolder = viewHolder;
        viewHolder.textDate.setText(quote.getDate());
        viewHolder.textBody.setText(quote.getText());
        viewHolder.textId.setText("#" + String.valueOf(quote.getId()));
        viewHolder.textBody.setOnClickListener(this);
        if (checked){
            setBtnHeight(1f);
        } else {
            setBtnHeight(0f);
        }
    }

    public void setBtnHeight(float value){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) quoteViewHolder.btnShare.getLayoutParams();
        params.height = (int) (value * Constants.density * 48);
        quoteViewHolder.btnShare.setLayoutParams(params);
        quoteViewHolder.btnShare.setAlpha(value);
    }

    @Override
    public void onClick(View v) {
        checked = !checked;
        ValueAnimator valueAnimator;
        if (checked){
            valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        } else {
            valueAnimator = ValueAnimator.ofFloat(1f, 0f);
        }

        valueAnimator.setDuration(400);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                setBtnHeight(value);
            }
        });
        valueAnimator.start();

    }
}
