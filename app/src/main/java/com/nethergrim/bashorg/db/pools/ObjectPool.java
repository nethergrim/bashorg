package com.nethergrim.bashorg.db.pools;

import android.util.Log;

import java.util.Stack;

/**
 * @author andreydrobyazko on 5/29/15 (andrew.drobyazko@chaoticmoon.com).
 */
public class ObjectPool<T> implements Pool<T> {

    public static final int DEFAULT_SIZE = 1000;
    public static final String TAG = ObjectPool.class.getSimpleName();
    private final Stack<T> freeObjects;
    protected PoolObjectFactory<T> factory;

    public ObjectPool() {
        freeObjects = new Stack<T>();
    }

    @Override
    public void recycle(T data) {
        freeObjects.push(data);
    }

    @Override
    public T get() {
        if (freeObjects.isEmpty()) {
            Log.e(TAG, "alert! creating new object " + toString());
            return factory.newObject();
        }
        return freeObjects.pop();
    }

    @Override
    public void setFactory(PoolObjectFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public void reset() {
        freeObjects.clear();
    }

    @Override
    public String toString() {
        return "Current Pool Size: " + freeObjects.size();
    }
}
