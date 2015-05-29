package com.nethergrim.bashorg.db.pools;

/**
 * @author andreydrobyazko on 5/29/15 (andrew.drobyazko@chaoticmoon.com).
 */
public interface PoolObjectFactory<T> {
    public T newObject();
}
