package com.nethergrim.bashorg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nethergrim.bashorg.model.Quote;

import java.util.List;

/**
 * Created by andrey_drobyazko on 09.12.14 19:21.
 */
public class DB {

    public static final String DB_NAME = "db";
    private static final int DB_VERSION = 1;
    private Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    private static DB db;


    private static final String CREATE_TABLE_QUOTES = "create table "
            + Quote.Columns.TABLE + "("
            + Quote.Columns.FIELD_ID + " integer primary key , "
            + Quote.Columns.FIELD_BODY + " text, "
            + Quote.Columns.FIELD_PAGE + " integer, "
            + Quote.Columns.FIELD_RATING + " integer, "
            + Quote.Columns.FIELD_DATE + " text, "
            + Quote.Columns.FIELD_INDEX_ON_PAGE + " integer " +
            "UNIQUE(" + Quote.Columns.FIELD_ID + ") ON CONFLICT REPLACE" + ");";


    public static void init(Context context){
        if (db == null){
            db = new DB(context);
        }
        db.open();
    }

    public static DB getInstance(){
        if (db == null) {
            Log.e("TAG", ":::" + "DB IS NOT INITIALISED");
        }
        return db;
    }

    private DB(Context ctx) {
        mCtx = ctx;
    }

    private void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        try {
            if (mDBHelper != null)
                mDBHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persist(Quote quote){
        ContentValues cv = new ContentValues();
        cv.put(Quote.Columns.FIELD_ID, quote.getId());
        cv.put(Quote.Columns.FIELD_BODY, quote.getText());
        cv.put(Quote.Columns.FIELD_DATE, quote.getDate());
        cv.put(Quote.Columns.FIELD_INDEX_ON_PAGE, quote.getIndexOnPage());
        cv.put(Quote.Columns.FIELD_PAGE, quote.getPage());
        cv.put(Quote.Columns.FIELD_RATING, quote.getRating());
        mDB.insert(Quote.Columns.TABLE, null, cv);
    }

    public void persist(List<Quote> quotes){
        mDB.beginTransaction();
        try{
            for (Quote quote : quotes) {
                persist(quote);
            }
            mDB.setTransactionSuccessful();
        } finally {
            mDB.endTransaction();
        }
    }

    public Cursor getQuotesFromEnd(int limit)


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_QUOTES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}