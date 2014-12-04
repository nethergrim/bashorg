package com.nethergrim.bashorg.row;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andrey_drobyazko on 02.12.14 19:59.
 */
public interface Row {
    public View getView(View view, LayoutInflater inflater);
    public RowType getRowType();
    public long getId();
}
