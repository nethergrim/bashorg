package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.model.Quote;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class QuoteAdapter extends RealmBaseAdapter<Quote> {


    public QuoteAdapter(Context context, RealmResults<Quote> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null){
            v = inflater.inflate(R.layout.row_quote, viewGroup, false);
            ViewHolder holder = new ViewHolder();
            holder.textBody = (TextView) v.findViewById(R.id.text_body);
            holder.textDate = (TextView) v.findViewById(R.id.text_date);
            holder.textId = (TextView) v.findViewById(R.id.text_id);
            v.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) v.getTag();
        Quote quote = realmResults.get(i);
        holder.textBody.setText(quote.getText());
        holder.textId.setText("#" + String.valueOf(quote.getId()));
        holder.textDate.setText(quote.getDate());
        return v;
    }

    private static class ViewHolder{
        TextView textId;
        TextView textBody;
        TextView textDate;
    }
}
