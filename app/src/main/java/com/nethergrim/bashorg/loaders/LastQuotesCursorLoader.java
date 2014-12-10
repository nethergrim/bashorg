package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 10.12.14 18:55.
 */
public class LastQuotesCursorLoader extends CursorLoader {
    public LastQuotesCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        DB db = DB.getInstance();
        return db.getQuotesFromEnd();
    }
}
