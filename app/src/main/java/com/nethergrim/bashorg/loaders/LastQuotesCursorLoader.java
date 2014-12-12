package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 10.12.14 18:55.
 */
public class LastQuotesCursorLoader extends CursorLoader {

    final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

    public LastQuotesCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        DB db = DB.getInstance();
        Cursor c =  db.getQuotesFromEnd();
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
        }
        return c;
    }
}
