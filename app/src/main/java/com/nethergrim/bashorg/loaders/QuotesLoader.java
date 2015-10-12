package com.nethergrim.bashorg.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.model.QuoteSelection;

/**
 * Created by nethergrim on 13.01.2015.
 */
public class QuotesLoader extends CursorLoader {

    protected final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    private QuoteSelection selection;
    private int limit = 0;
    private boolean mAutoUpdate = true;

    public QuotesLoader(Context context, Bundle args) {
        super(context);
        this.limit = args.getInt(Constants.EXTRA_LIMIT);
        this.selection = (QuoteSelection) args.getSerializable(Constants.EXTRA_QUOTE_SELECTION);
    }

    public QuotesLoader(Context context, Bundle args, boolean autoUpdate) {
        super(context);
        this.limit = args.getInt(Constants.EXTRA_LIMIT);
        this.selection = (QuoteSelection) args.getSerializable(Constants.EXTRA_QUOTE_SELECTION);
        this.mAutoUpdate = autoUpdate;
    }

    protected void registerCursor(Cursor c, String uri){
        if (c != null && mAutoUpdate) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), Uri.parse(uri));
        }
    }

    @Override
    public Cursor loadInBackground() {
        DB db = DB.getInstance();
        Cursor c = db.fetch(selection, limit);
        registerCursor(c, Constants.URI_QUOTE);
        return c;
    }


}
