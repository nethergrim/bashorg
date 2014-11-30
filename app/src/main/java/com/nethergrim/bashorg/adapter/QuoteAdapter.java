package com.nethergrim.bashorg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.model.Quote;

import io.realm.RealmResults;

/**
 * Created by nethergrim on 30.11.2014.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteViewHolder> {

    private RealmResults<Quote> quotes;

    public QuoteAdapter(RealmResults<Quote> results) {
        this.quotes = results;
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_quote, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        QuoteViewHolder vh = new QuoteViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder holder, int i) {
        Quote quote = quotes.get(i);
        holder.textDate.setText(quote.getDate());
        holder.textBody.setText(quote.getText());
        holder.textId.setText("#" + String.valueOf(quote.getId()));
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }
}
