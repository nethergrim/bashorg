package com.nethergrim.bashorg.row;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by nethergrim on 29.11.2014.
 */
public interface Row {

    public RowType getRowType();

    public View getView(LayoutInflater inflater, View view);

    public long getId();
}
