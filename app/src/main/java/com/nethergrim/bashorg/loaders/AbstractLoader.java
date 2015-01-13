package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.Constants;

/**
 * Created by nethergrim on 13.01.2015.
 */
public abstract class AbstractLoader extends CursorLoader {

    protected final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    private int limit = 0;

    public AbstractLoader(Context context, Bundle args) {
        super(context);
        this.limit = args.getInt(Constants.EXTRA_LIMIT);
    }

    protected void registerCursor(Cursor c, String uri){
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), Uri.parse(uri));
        }
    }

    protected int getLimit(){
        return limit;
    }

}
