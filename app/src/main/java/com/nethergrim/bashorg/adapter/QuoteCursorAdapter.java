package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by andrey_drobyazko on 09.12.14 20:21.
 */
public class QuoteCursorAdapter extends CursorAdapter {


    public QuoteCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public QuoteCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public QuoteCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
