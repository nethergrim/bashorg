package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.viewholder.QuoteViewHolder;
import com.nethergrim.bashorg.db.QuoteInflater;
import com.nethergrim.bashorg.model.Quote;

/**
 * @author andrey_drobyazko on 09.12.14 20:21.
 */
public class QuoteCursorAdapter extends CursorAdapter {

    private QuoteInflater mInflater;

    public QuoteCursorAdapter(Context context) {
        super(context, null, 0);
        mInflater = new QuoteInflater();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_quote, parent, false);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(v);
        v.setTag(quoteViewHolder);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final QuoteViewHolder vh = (QuoteViewHolder) view.getTag();
        Quote q = mInflater.inflateEntityAtCurrentPosition(cursor);
        vh.bindQuouteData(q, context);
    }



}
