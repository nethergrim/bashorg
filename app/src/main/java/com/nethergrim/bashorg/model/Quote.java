package com.nethergrim.bashorg.model;


import com.nethergrim.bashorg.db.pools.QuotePoolHolder;

import java.io.Serializable;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class Quote implements Serializable {

    private String text;
    private String date;
    private int indexOnPage;
    private long id;
    private long page;
    private long rating;
    private boolean liked = false;

    public Quote() {
    }

    public static Quote newInstance() {
        return QuotePoolHolder.getQuotePool().acquire();
    }

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

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public int getIndexOnPage() {
        return indexOnPage;
    }

    public void setIndexOnPage(int indexOnPage) {
        this.indexOnPage = indexOnPage;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void recycle() {
        QuotePoolHolder.getQuotePool().release(this);
    }

    public interface Columns {
        String TABLE = "quote_table";

        String FIELD_ID = "_id";
        String FIELD_BODY = "body";
        String FIELD_PAGE = "page";
        String FIELD_RATING = "rating";
        String FIELD_DATE = "date";
        String FIELD_INDEX_ON_PAGE = "index_on_page";
        String FIELD_LIKED = "liked";
    }
}
