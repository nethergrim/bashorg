package com.nethergrim.bashorg.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nethergrim.bashorg.R;

/**
 * Created by nethergrim on 30.11.2014.
 */
public class QuoteViewHolder extends RecyclerView.ViewHolder {

    public TextView textId;
    public TextView textDate;
    public TextView textBody;
    public CardView cardView;
    public ImageButton btnShare;

    public QuoteViewHolder(View v) {
        super(v);
        textId = (TextView) v.findViewById(R.id.text_id);
        textBody = (TextView) v.findViewById(R.id.text_body);
        textDate = (TextView) v.findViewById(R.id.text_date);
        cardView = (CardView) v.findViewById(R.id.card);
        btnShare = (ImageButton) v.findViewById(R.id.btn_share);
    }
}
