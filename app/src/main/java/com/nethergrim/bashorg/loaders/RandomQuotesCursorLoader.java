package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 11.12.14 19:30.
 */
public class RandomQuotesCursorLoader  extends CursorLoader{

    final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

    public RandomQuotesCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        DB db = DB.getInstance();
        Cursor c = db.getQuotesRandomly();
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
        }
        return c;
    }
}
