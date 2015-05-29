package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.db.QuoteInflater;
import com.nethergrim.bashorg.model.Quote;

/**
 * Created by andrey_drobyazko on 09.12.14 20:21.
 */
public class QuoteCursorAdapter extends CursorAdapter {

    public QuoteCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_quote, null);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(v);
        v.setTag(quoteViewHolder);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final QuoteViewHolder quoteViewHolder = (QuoteViewHolder) view.getTag();
        Quote quote = new QuoteInflater().inflateEntityAtCurrentPosition(cursor);


        final String quoteText = quote.getText();
        final long quoteId = quote.getId();
        final boolean quoteIsLiked = !quote.isLiked();

        quoteViewHolder.textId.setText("#" + String.valueOf(quote.getId()));
        quoteViewHolder.textBody.setText(quote.getText());
        quoteViewHolder.textDate.setText(quote.getDate());
        quoteViewHolder.textRating.setText(String.valueOf(quote.getRating()));
        if (quote.isLiked()) {
            quoteViewHolder.btnLike.setImageResource(R.drawable.ic_action_favorite);
        } else {
            quoteViewHolder.btnLike.setImageResource(R.drawable.ic_action_favorite_outline);
        }
        quoteViewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.ACTION_SHARE_QUOTE);
                intent.putExtra(Constants.EXTRA_QUOTE, quoteText);
                context.sendBroadcast(intent);
            }
        });
        quoteViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getInstance().setQuoteLiked(quoteId, quoteIsLiked);
                if (quoteIsLiked) {
                    quoteViewHolder.btnLike.setImageResource(R.drawable.ic_action_favorite);
                } else {
                    quoteViewHolder.btnLike.setImageResource(R.drawable.ic_action_favorite_outline);
                }
            }
        });
        quote.recycle();
        quote = null;
    }

    public static class QuoteViewHolder {

        public TextView textId;
        public TextView textDate;
        public TextView textBody;
        public ImageButton btnShare;
        public ImageButton btnLike;
        public TextView textRating;

        public QuoteViewHolder(View v) {
            textId = (TextView) v.findViewById(R.id.text_id);
            textBody = (TextView) v.findViewById(R.id.text_body);
            textDate = (TextView) v.findViewById(R.id.text_date);
            textRating = (TextView) v.findViewById(R.id.text_rating);
            btnShare = (ImageButton) v.findViewById(R.id.btn_share);
            btnLike = (ImageButton) v.findViewById(R.id.btn_like);
        }
    }
}
