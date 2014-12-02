package com.nethergrim.bashorg.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nethergrim.bashorg.row.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nethergrim on 30.11.2014.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteViewHolder> {

    private List<Row<QuoteViewHolder>> rows;

    public void addRow(Row<QuoteViewHolder> row){
        getRows().add(row);
    }

    public List<Row<QuoteViewHolder>> getRows() {
        if (rows == null){
            rows = new ArrayList<Row<QuoteViewHolder>>();
        }
        return rows;
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getRows().get(i).onCreateViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder holder, int i) {
        getRows().get(i).onBindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return getRows().size();
    }
}
