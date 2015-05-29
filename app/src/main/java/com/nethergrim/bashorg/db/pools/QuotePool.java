package com.nethergrim.bashorg.db.pools;

import com.nethergrim.bashorg.model.Quote;

/**
 * @author andreydrobyazko on 5/29/15 (andrew.drobyazko@chaoticmoon.com).
 */
public class QuotePool extends ObjectPool<Quote> {

    public QuotePool() {
        setFactory(new QuoteFactory());
        for (int i = 0, size = DEFAULT_SIZE; i < size; i++) {
            recycle(factory.newObject());
        }
    }
}
