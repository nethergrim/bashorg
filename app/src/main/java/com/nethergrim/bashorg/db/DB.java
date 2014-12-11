package com.nethergrim.bashorg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
            + Quote.Columns.TABLE + "( "
            + Quote.Columns.FIELD_ID + " integer primary key , "
            + Quote.Columns.FIELD_BODY + " text, "
            + Quote.Columns.FIELD_PAGE + " integer, "
            + Quote.Columns.FIELD_RATING + " integer, "
            + Quote.Columns.FIELD_DATE + " text, "
            + Quote.Columns.FIELD_INDEX_ON_PAGE + " integer, " +
            "UNIQUE (" + Quote.Columns.FIELD_ID + ") ON CONFLICT REPLACE" + " );";


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
        mDB.insertWithOnConflict(Quote.Columns.TABLE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public boolean isQuoteSaved(Quote quote){
        long size = DatabaseUtils.queryNumEntries(mDB, Quote.Columns.TABLE, Quote.Columns.FIELD_ID + "=?", new String[]{String.valueOf(quote.getId())});
        return size > 0;
    }

    public boolean isPageSaved(String pageNumber){
        long size = DatabaseUtils.queryNumEntries(mDB, Quote.Columns.TABLE, Quote.Columns.FIELD_PAGE + "=?", new String[]{pageNumber});
        return size > 49;
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

    public Cursor getQuotesFromEnd(){
        return mDB.query(Quote.Columns.TABLE, null,null,null,null,null, Quote.Columns.FIELD_ID + " DESC");
    }

    public Cursor getQuotesRandomly(){
        return mDB.query(Quote.Columns.TABLE, null,null,null,null,null, " RANDOM()");
    }

    public Cursor getQuotesFromEnd(int limit){
        return mDB.query(Quote.Columns.TABLE, null,null,null,null,null, Quote.Columns.FIELD_ID + " DESC" , String.valueOf(limit));
    }

    public Cursor getQuotesFrom(int limit, long idToQueryFrom){
        return mDB.query(Quote.Columns.TABLE, null,Quote.Columns.FIELD_ID + "< ?", new String[]{String.valueOf(idToQueryFrom)},null,null, Quote.Columns.FIELD_ID + " DESC" , String.valueOf(limit));
    }

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
