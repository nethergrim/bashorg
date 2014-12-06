package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nethergrim.bashorg.row.Row;
import com.nethergrim.bashorg.row.RowType;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter<T extends Row> extends BaseAdapter {
    protected final List<T> rows;
    protected LayoutInflater inflater;

    public ListViewAdapter(Context activity) {
        this.rows = new ArrayList<T>();
        if (activity != null)
            this.inflater = LayoutInflater.from(activity);
    }

    public void deleteRow(T row) {
        rows.remove(row);
        notifyDataSetChanged();
    }

    public void removeRow(int position){
        rows.remove(position);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        rows.clear();
    }

    public void addRow(T row) {
        for (T t : rows) {
            if (t.getId() == row.getId()) {
                return;
            }
        }
        rows.add(row);
    }

//    public void addRow(int position, T row){
//        rows.add(position, row);
//    }

//    public void addRows(List<T> rows) {
//        this.rows.addAll(rows);
//        notifyDataSetChanged();
//    }

    public List<T> getRows() {
        return rows;
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > rows.size() - 1 || position < 0) {
            return 0;
        } else
            return rows.get(position).getRowType().ordinal();
    }

    public int getCount() {
        return rows.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T row = rows.get(position);
        return row.getView(convertView, inflater);
    }

    public long getId(int position){
        return getRows().get(position).getId();
    }
}