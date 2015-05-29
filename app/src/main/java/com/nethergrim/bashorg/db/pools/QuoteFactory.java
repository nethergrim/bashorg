package com.nethergrim.bashorg.db.pools;

import com.nethergrim.bashorg.model.Quote;

/**
 * @author andreydrobyazko on 5/29/15 (andrew.drobyazko@chaoticmoon.com).
 */
public class QuoteFactory implements PoolObjectFactory<Quote> {
    @Override
    public Quote newObject() {
        return new Quote();
    }
}
