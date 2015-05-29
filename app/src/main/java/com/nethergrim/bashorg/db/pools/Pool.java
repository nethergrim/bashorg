package com.nethergrim.bashorg.db.pools;

/**
 * @author andreydrobyazko on 5/29/15 (andrew.drobyazko@chaoticmoon.com).
 */
public interface Pool<T> {
    public void recycle(final T data);

    public T get();

    public void setFactory(final PoolObjectFactory<T> factory);

    public void reset();
}
