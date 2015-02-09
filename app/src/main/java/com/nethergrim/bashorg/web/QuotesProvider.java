package com.nethergrim.bashorg.web;

/**
 * Created by nethergrim on 09.02.2015.
 */
public class QuotesProvider implements Runnable {

    private int page;
    private QuouteBuffer buffer;

    public QuotesProvider(int page, QuouteBuffer buffer) {
        this.page = page;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            buffer.add(BashorgParser.getQuotesFromPage(page));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
