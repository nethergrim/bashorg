package com.nethergrim.bashorg.web;

import com.nethergrim.bashorg.db.DB;

/**
 * Created by nethergrim on 09.02.2015.
 */
public class QuotesReceiver implements Runnable {

    private QuouteBuffer buffer;

    public QuotesReceiver(QuouteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {DB db = DB.getInstance();
        while(true){
            try {
                db.persist(buffer.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
