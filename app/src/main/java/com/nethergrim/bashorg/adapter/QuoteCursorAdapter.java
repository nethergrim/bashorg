package com.nethergrim.bashorg.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.db.QuoteInflater;
import com.nethergrim.bashorg.model.Quote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey_drobyazko on 09.12.14 20:21.
 */
public class QuoteCursorAdapter extends CursorAdapter {

    public List<Long> selectedRows = new ArrayList<>();

    public QuoteCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public QuoteCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public QuoteCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private void toggle(View view, long id){

    }

    private boolean isChecked(long id){
        for (Long selectedRow : selectedRows) {
            if (id == selectedRow) return true;
        }
        return false;
    }

    private void check(long id){
        if (!isChecked(id)){
            selectedRows.add(id);
        }
    }

    private void unCheck(long id){
        for (int i = 0; i < selectedRows.size(); i++) {
            if (selectedRows.get(i) == id){
                selectedRows.remove(i);
                break;
            }
        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_quote, null);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(v);
        v.setTag(quoteViewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final QuoteViewHolder quoteViewHolder = (QuoteViewHolder) view.getTag();
        final Quote quote = new QuoteInflater().inflateEntityAtCurrentPosition(cursor);
        quoteViewHolder.textId.setText("#" + String.valueOf(quote.getId()));
        quoteViewHolder.textBody.setText(quote.getText());
        quoteViewHolder.textDate.setText(quote.getDate());
        quoteViewHolder.textRating.setText(String.valueOf(quote.getRating()));
        if (Build.VERSION.SDK_INT >= 21){
            quoteViewHolder.cardView.setCardElevation(8);
        }
        if (isChecked(quote.getId())){
            setBtnHeight(1, quoteViewHolder);
        } else {
            setBtnHeight(0, quoteViewHolder);
        }
        quoteViewHolder.textBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = quote.getId();
                ValueAnimator animator;
                if (isChecked(id)){
                    animator = ValueAnimator.ofFloat(1f, 0f);
                    unCheck(id);
                } else {
                    animator = ValueAnimator.ofFloat(0f, 1f);
                    check(id);
                }
                animator.setDuration(Constants.ANIMATION_DURATION);
                animator.setInterpolator(Constants.INTERPOLATOR);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float value = (Float) animation.getAnimatedValue();
                        setBtnHeight(value, quoteViewHolder);
                    }
                });
                animator.start();
            }
        });
        quoteViewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static class QuoteViewHolder{

        public TextView textId;
        public TextView textDate;
        public TextView textBody;
        public CardView cardView;
        public ImageButton btnShare;
        public TextView textRating;

        public QuoteViewHolder(View v) {
            textId = (TextView) v.findViewById(R.id.text_id);
            textBody = (TextView) v.findViewById(R.id.text_body);
            textDate = (TextView) v.findViewById(R.id.text_date);
            textRating = (TextView) v.findViewById(R.id.text_rating);
            cardView = (CardView) v.findViewById(R.id.card);
            btnShare = (ImageButton) v.findViewById(R.id.btn_share);
        }
    }

    public void setBtnHeight(float value, QuoteViewHolder viewHolder){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.btnShare.getLayoutParams();
        params.height = (int) (value * Constants.density * 48);
        viewHolder.btnShare.setLayoutParams(params);
        viewHolder.btnShare.setAlpha(value);
    }
}
