package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 11.12.14 19:42.
 */
@Deprecated
public class BestQuotesCursorLoader extends QuotesLoader {


    public BestQuotesCursorLoader(Context context, Bundle args) {
        super(context, args);
    }

    @Override
    public Cursor loadInBackground() {
//        DB db = DB.getInstance();
//        Cursor c =  db.getQuotesByRating(getLimit());
//        registerCursor(c, Constants.URI_QUOTE);
//        return c;
        return null;
    }

}
