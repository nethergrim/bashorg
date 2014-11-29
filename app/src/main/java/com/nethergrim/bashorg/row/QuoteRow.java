package com.nethergrim.bashorg.row;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.model.Quote;

/**
 * Created by nethergrim on 29.11.2014.
 */
public class QuoteRow implements Row {

    private Quote quote;

    public QuoteRow(Quote quote) {
        this.quote = quote;
    }

    @Override
    public RowType getRowType() {
        return RowType.QUOTE;
    }

    @Override
    public View getView(LayoutInflater inflater, View view) {
        View v = view;
        if (v == null) {
            v = inflater.inflate(R.layout.row_quote, null);
            ViewHolder holder = new ViewHolder();
            holder.textId = (TextView) v.findViewById(R.id.text_id);
            holder.textBody = (TextView) v.findViewById(R.id.text_body);
            holder.textDate = (TextView) v.findViewById(R.id.text_date);
            v.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) v.getTag();
        holder.textId.setText("#" + quote.getId());
        holder.textDate.setText(quote.getDate());
        holder.textBody.setText(quote.getText());
        return v;
    }

    @Override
    public long getId() {
        return quote.getId();
    }

    private class ViewHolder {
        TextView textId;
        TextView textBody;
        TextView textDate;
    }
}
