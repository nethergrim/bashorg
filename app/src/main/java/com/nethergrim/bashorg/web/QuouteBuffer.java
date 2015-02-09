package com.nethergrim.bashorg.web;

import android.util.Log;

import com.nethergrim.bashorg.model.Quote;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nethergrim on 09.02.2015.
 */
public class QuouteBuffer {

    public static final int BUFFER_SIZE = 10000;
    private List<Quote> quotesList;
    private int insertionCount = 0;

    public QuouteBuffer() {
        quotesList = new LinkedList<>();
    }

    public synchronized void add(List<Quote> quotes) throws InterruptedException {
        if (quotesList.size() + quotes.size() > BUFFER_SIZE){
                this.wait();
        }
        quotesList.addAll(quotes);
        insertionCount++;
        Log.e("TAG", "added: " + insertionCount + " " + quotes.size());
        this.notifyAll();
    }

    public synchronized List<Quote> get() throws InterruptedException {
        List<Quote> result;
        if (quotesList.size() < 1){
                this.wait();
        }
        result = new ArrayList<>(quotesList);
        quotesList.clear();
        this.notifyAll();
        return result;
    }


}
