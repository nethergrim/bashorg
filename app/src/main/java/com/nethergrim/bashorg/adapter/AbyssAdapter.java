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

    private RealmResults<Abyss> data;

    public AbyssAdapter() {
    }

    public AbyssAdapter(RealmResults<Abyss> data) {
        this.data = data;
    }

    public void setData(RealmResults<Abyss> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public AbyssVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_abyss, parent, false);
        return new AbyssVh(v);
    }

    @Override
    public void onBindViewHolder(AbyssVh holder, int position) {
        Abyss abyss = data.get(position);
        holder.mTVBody.setText(abyss.getBody());
        holder.mTVDate.setText(abyss.getDate());
        holder.mTVId.setText(abyss.getId());
    }

    public String getNextPageFromLast() {
        if (data != null && !data.isEmpty()) {
            Abyss last = data.get(data.size() - 1);
            return last.getNextPageDate();
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }

    }
}
