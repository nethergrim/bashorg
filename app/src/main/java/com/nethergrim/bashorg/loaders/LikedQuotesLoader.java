package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.db.DB;

/**
 * Created by andrey_drobyazko on 09.01.15 20:59.
 */
public class LikedQuotesLoader extends AbstractLoader {

    public LikedQuotesLoader(Context context, Bundle args) {
        super(context, args);
    }

    @Override
    public Cursor loadInBackground() {
        DB db = DB.getInstance();
        Cursor c =  db.getQuotesLiked(getLimit());
        registerCursor(c, Constants.URI_QUOTE);
        return c;
    }
}

