package com.nethergrim.bashorg.row;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by andrey_drobyazko on 02.12.14 19:59.
 */
public interface Row<T extends RecyclerView.ViewHolder>  {
    public T onCreateViewHolder(ViewGroup viewGroup);
    public void onBindViewHolder(T viewHolder);
}
