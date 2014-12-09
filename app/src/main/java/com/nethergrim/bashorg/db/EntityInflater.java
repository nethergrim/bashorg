package com.nethergrim.bashorg.db;

/**
 * Created by andrey_drobyazko on 09.12.14 19:27.
 */

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Inflater of SQLite Cursor into Data Transfer Objects
 *
 * @param <T> Desired type of entity to be inflated with sql data
 */
public abstract class EntityInflater<T> {

    /**
     * Returns object list inflated with SQLite data
     *
     * @param c Cursor to get data from
     * @return
     */
    public List<T> inflateEntity(Cursor c) {
        List<T> entities = new ArrayList<T>();
        if (c == null) return entities;
        while (c.moveToNext()) {
            entities.add(inflateEntityAtCurrentPosition(c));
        }
        return entities;
    }

    /**
     * Returns object inflated with SQLite data
     *
     * @param c          Cursor to get data from
     * @param atPosition position to take object from. The implementation should
     *                   invoke cursor.moveToPosition before extracting data
     * @return
     */
    public T inflateEntity(Cursor c, int atPosition) {
        c.moveToPosition(atPosition);
        return inflateEntityAtCurrentPosition(c);
    }

    /**
     * Returns object inflated with SQLite data
     *
     * @param c Cursor to get data from. Be aware that it doesn't move the
     *          cursor and reads from the currently set position. That is for
     *          better performance when using CursorAdapter
     * @return
     */
    public abstract T inflateEntityAtCurrentPosition(Cursor c);
}
