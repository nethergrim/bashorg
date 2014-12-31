package com.nethergrim.bashorg.views;

import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nethergrim.bashorg.callback.OnTopBarHeightListener;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by nethergrim on 30.12.2014.
 */
public class Scroller implements AbsListView.OnScrollListener {

    private OnTopBarHeightListener callback;
    private int mPosition = 0;
    private int mOffset = 0;

    public Scroller(final OnTopBarHeightListener callback) {
        this.callback = callback;
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        int position = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        int offset = (v == null) ? 0 : v.getTop();

        if (mPosition < position || (mPosition == position && mOffset < offset)) {
            callback.onTopBarHeightChanged(false);
        } else {
            callback.onTopBarHeightChanged(true);
        }
        mPosition = position;
        mOffset = offset;

}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
