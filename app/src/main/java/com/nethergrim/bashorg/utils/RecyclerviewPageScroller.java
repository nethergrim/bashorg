package com.nethergrim.bashorg.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class RecyclerviewPageScroller extends RecyclerView.OnScrollListener {

    private int mDefaultPageSize = 10;
    private OnRecyclerViewScrolledToPageListener mCallback;
    private OnRecyclerScrolledToEndCallback mScrolledToEndCallback;
    private int mLastDeliveredPage;
    private int mOffset;

    public RecyclerviewPageScroller(int defaultPageSize, OnRecyclerViewScrolledToPageListener callback, int offset) {
        this.mDefaultPageSize = defaultPageSize;
        this.mCallback = callback;
        this.mOffset = offset;
        if (mCallback == null) {
            throw new IllegalStateException("OnRecyclerViewScrolledToPageListener is NULL");
        }
        if (mOffset < 0) {
            mOffset *= -1;
        }
    }

    public RecyclerviewPageScroller(int offset, OnRecyclerScrolledToEndCallback scrolledToEndCallback) {
        this.mOffset = offset;
        this.mScrolledToEndCallback = scrolledToEndCallback;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

        int lastVisiblePositionWithOffset = firstVisibleItemPosition + visibleItemCount + mOffset;
        int totalAmount = recyclerView.getAdapter().getItemCount();
        if (lastVisiblePositionWithOffset + mOffset >= totalAmount && mScrolledToEndCallback != null) {
            mScrolledToEndCallback.onRecyclerScrolledToEnd();
        }
        if (lastVisiblePositionWithOffset % mDefaultPageSize == 0) {
            int newPageNumber = lastVisiblePositionWithOffset / mDefaultPageSize;
            if (mLastDeliveredPage != newPageNumber) {
                mLastDeliveredPage = newPageNumber;
                if (mCallback != null) {
                    mCallback.onRecyclerViewScrolledToPage(newPageNumber);
                }
            }

        }

    }

    public interface OnRecyclerScrolledToEndCallback {
        void onRecyclerScrolledToEnd();
    }

    public interface OnRecyclerViewScrolledToPageListener {
        void onRecyclerViewScrolledToPage(int pageNumber);
    }
}
