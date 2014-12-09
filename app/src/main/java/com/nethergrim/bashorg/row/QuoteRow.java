package com.nethergrim.bashorg.row;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.callback.OnQuoteSharePressed;
import com.nethergrim.bashorg.model.Quote;

/**
* Created by andrey_drobyazko on 02.12.14 20:01.
*/
public class QuoteRow implements Row, View.OnClickListener {

    public void setBtnHeight(float value){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) quoteViewHolder.btnShare.getLayoutParams();
        params.height = (int) (value * Constants.density * 48);
        quoteViewHolder.btnShare.setLayoutParams(params);
        quoteViewHolder.btnShare.setAlpha(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_body:
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
                break;
            case R.id.btn_share:
                callback.onQuoteSharePressed(quote);
                break;
        }
    }


    public View getView(View view, LayoutInflater inflater) {
        View v = view;
        if (v == null){
            v = inflater.inflate(R.layout.row_quote, null);
            quoteViewHolder = new QuoteViewHolder(v);
            if (Build.VERSION.SDK_INT >= 21){
                quoteViewHolder.cardView.setCardElevation(8);
            }
            v.setTag(quoteViewHolder);
        }
        quoteViewHolder = (QuoteViewHolder) v.getTag();
        quoteViewHolder.textDate.setText(quote.getDate());
        quoteViewHolder.textBody.setText(quote.getText());
        quoteViewHolder.textId.setText("#" + String.valueOf(quote.getId()));
        quoteViewHolder.textBody.setOnClickListener(this);
        quoteViewHolder.btnShare.setOnClickListener(this);
        if (checked){
            setBtnHeight(1f);
        } else {
            setBtnHeight(0f);
        }
        return v;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }



}
