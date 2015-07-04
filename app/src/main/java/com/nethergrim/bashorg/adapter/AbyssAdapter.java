package com.nethergrim.bashorg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.viewholder.AbyssVh;
import com.nethergrim.bashorg.model.Abyss;
import io.realm.RealmResults;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class AbyssAdapter extends RecyclerView.Adapter<AbyssVh> {

    RealmResults<Abyss> data;

    public AbyssAdapter(RealmResults<Abyss> data) {
        this.data = data;
    }

    @Override
    public AbyssVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_abyss, parent, false);
        AbyssVh vh = new AbyssVh(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AbyssVh holder, int position) {
        Abyss abyss = data.get(position);
        holder.mTVBody.setText(abyss.getBody());
        holder.mTVDate.setText(abyss.getDate());
        holder.mTVId.setText(abyss.getId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
