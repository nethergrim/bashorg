package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;

/**
 * Created by andrey_drobyazko on 09.12.14 20:21.
 */
public class QuoteCursorAdapter extends CursorAdapter {


    public QuoteCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public QuoteCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public QuoteCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    public static class QuoteViewHolder{

        public TextView textId;
        public TextView textDate;
        public TextView textBody;
        public CardView cardView;
        public ImageButton btnShare;

        public QuoteViewHolder(View v) {
            textId = (TextView) v.findViewById(R.id.text_id);
            textBody = (TextView) v.findViewById(R.id.text_body);
            textDate = (TextView) v.findViewById(R.id.text_date);
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
