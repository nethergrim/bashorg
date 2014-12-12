package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 11.12.14 19:42.
 */
public class BestQuotesCursorLoader extends CursorLoader {

    public BestQuotesCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        DB db = DB.getInstance();
        return db.getQuotesByRating();
    }


}
