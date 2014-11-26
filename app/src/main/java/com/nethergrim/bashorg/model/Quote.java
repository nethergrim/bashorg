package com.nethergrim.bashorg.model;

import io.realm.RealmObject;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class Quote extends RealmObject {

    private String text;
    private long id;
    private String date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
