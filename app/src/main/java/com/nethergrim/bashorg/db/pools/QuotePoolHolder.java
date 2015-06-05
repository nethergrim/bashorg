package com.nethergrim.bashorg.db.pools;

import android.support.v4.util.Pools;

import com.nethergrim.bashorg.model.Quote;

/**
 * @author andreydrobyazko on 5/29/15 (andrew.drobyazko@chaoticmoon.com).
 */
public class QuotePoolHolder {

    public static final int DEFAULT_SIZE = 1000;
    private static final Pools.SimplePool<Quote> quotePool = new Pools.SimplePool<>(DEFAULT_SIZE);
    private static boolean mPoolIsInited = false;

    public synchronized static Pools.SimplePool<Quote> getQuotePool() {
        if (!mPoolIsInited) {
            mPoolIsInited = true;
            for (int i = 0; i < DEFAULT_SIZE; i++) {
                quotePool.release(new Quote());
            }
        }
        return quotePool;
    }
}
