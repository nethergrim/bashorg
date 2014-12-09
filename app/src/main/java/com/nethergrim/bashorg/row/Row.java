package com.nethergrim.bashorg.row;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andrey_drobyazko on 02.12.14 19:59.
 */
public interface Row {
    public View newView(Context context, Cursor cursor, ViewGroup parent);
    public void bindView(View view, Context context, Cursor cursor);
}
