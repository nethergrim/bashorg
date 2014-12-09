package com.nethergrim.bashorg.db;

import android.database.Cursor;

import com.nethergrim.bashorg.model.Quote;

/**
 * Created by andrey_drobyazko on 09.12.14 19:27.
 */
public class QuoteInflater extends EntityInflater<Quote> implements Quote.Columns {


    @Override
    public Quote inflateEntityAtCurrentPosition(Cursor c) {
        Quote quote = new Quote();
        quote.setId(c.getLong(c.getColumnIndex(FIELD_ID)));
        quote.setText(c.getString(c.getColumnIndex(FIELD_BODY)));
        quote.setDate(c.getString(c.getColumnIndex(FIELD_DATE)));
        quote.setIndexOnPage(c.getInt(c.getColumnIndex(FIELD_INDEX_ON_PAGE)));
        quote.setPage(c.getLong(c.getColumnIndex(FIELD_PAGE)));
        quote.setRating(c.getLong(c.getColumnIndex(FIELD_RATING)));
        return quote;
    }
}
