package com.nethergrim.bashorg.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.nethergrim.bashorg.Constants;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class PаgeFetchedBroadcastReceiver extends BroadcastReceiver {

    private PageLoadedInterface mCallback;

    public PаgeFetchedBroadcastReceiver(Context context, PageLoadedInterface callback) {
        this.mCallback = callback;
        register(context);
    }


    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(Constants.ACTION_FETCH_PAGE));
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mCallback != null) {
            int pageNumber = intent.getIntExtra(Constants.EXTRA_PAGE_NUMBER, Integer.MAX_VALUE);
            mCallback.onPageLoaded(pageNumber);
        }
    }

    public interface PageLoadedInterface {
        void onPageLoaded(int pageNumber);
    }
}
