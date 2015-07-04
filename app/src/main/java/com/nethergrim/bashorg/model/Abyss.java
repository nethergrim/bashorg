package com.nethergrim.bashorg.model;

import io.realm.RealmObject;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class Abyss extends RealmObject {

    private String body;
    private String date;
    private String id;
    private String datePage;
    private String nextPageDate;
    private int indexOnPage;
    private int globalPageSize;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatePage() {
        return datePage;
    }

    public void setDatePage(String datePage) {
        this.datePage = datePage;
    }

    public int getIndexOnPage() {
        return indexOnPage;
    }

    public void setIndexOnPage(int indexOnPage) {
        this.indexOnPage = indexOnPage;
    }

    public int getGlobalPageSize() {
        return globalPageSize;
    }

    public void setGlobalPageSize(int globalPageSize) {
        this.globalPageSize = globalPageSize;
    }

    public String getNextPageDate() {
        return nextPageDate;
    }

    public void setNextPageDate(String nextPageDate) {
        this.nextPageDate = nextPageDate;
    }
}
