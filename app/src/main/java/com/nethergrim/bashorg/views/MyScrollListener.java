package com.nethergrim.bashorg.views;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.nethergrim.bashorg.callback.OnTopBarHeightListener;

/**
 * Created by nethergrim on 30.12.2014.
 */
public class MyScrollListener implements AbsListView.OnScrollListener {

    private OnTopBarHeightListener callback;

    public MyScrollListener(OnTopBarHeightListener callback) {
        this.callback = callback;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        view.setOnTouchListener(new View.OnTouchListener() {
            private float mInitialY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mInitialY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        final float y = event.getY();
                        final float yDiff = y - mInitialY;
                        if (yDiff > 0.0) {
                            callback.onTopBarHeightChanged(yDiff);
                            break;
                        } else if (yDiff < 0.0) {
                            callback.onTopBarHeightChanged(yDiff);
                            break;

                        }
                        break;
                }
                return false;
            }

        });

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

    }
}
