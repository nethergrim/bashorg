package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 10.12.14 18:55.
 */
@Deprecated
public class LastQuotesCursorLoader extends QuotesLoader {

    public LastQuotesCursorLoader(Context context, Bundle args) {
        super(context, args);
    }

    @Override
    public Cursor loadInBackground() {
//        DB db = DB.getInstance();
//        Cursor c =  db.getQuotesFromEnd(getLimit());
//        registerCursor(c, Constants.URI_QUOTE);
//        return c;
        return null;
    }
}
