package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 11.12.14 19:30.
 */
@Deprecated
public class RandomQuotesCursorLoader  extends CursorLoader{

    public RandomQuotesCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
//        DB db = DB.getInstance();
//        Cursor c = db.getQuotesRandomly(100);
//        if (c != null) {
//            c.getCount();
//        }
//        return c;
        return null;
    }

}
