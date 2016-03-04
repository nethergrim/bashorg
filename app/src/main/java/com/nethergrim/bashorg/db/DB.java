package com.nethergrim.bashorg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.model.QuoteSelection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by andrey_drobyazko on 09.12.14 19:21.
 */
public class DB {

    public static final String DB_NAME = "db";
    private static final int DB_VERSION = 2;
    private static final String CREATE_TABLE_QUOTES = "create table "
            + Quote.Columns.TABLE + "( "
            + Quote.Columns.FIELD_ID + " integer primary key , "
            + Quote.Columns.FIELD_BODY + " text, "
            + Quote.Columns.FIELD_PAGE + " integer, "
            + Quote.Columns.FIELD_RATING + " integer, "
            + Quote.Columns.FIELD_DATE + " text, "
            + Quote.Columns.FIELD_INDEX_ON_PAGE + " integer, "
            + Quote.Columns.FIELD_LIKED + " integer, " +
            "UNIQUE (" + Quote.Columns.FIELD_ID + ") ON CONFLICT REPLACE" + " );";
    private static DB db;
    private Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;


    private DB(Context ctx) {
        mCtx = ctx;
    }

    public static void init(Context context) {
        if (db == null) {
            db = new DB(context);
        }
        db.open();
    }

    public static DB getInstance() {
        if (db == null) {
            init(App.getInstance().getApplicationContext());
            Log.e("TAG", ":::" + "DB IS NOT INITIALISED");
        }
        return db;
    }

    public void close() {
        try {
            if (mDBHelper != null)
                mDBHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persist(Quote quote) {
        if (!isQuoteSaved(quote)) {
            ContentValues cv = new ContentValues();
            cv.put(Quote.Columns.FIELD_ID, quote.getId());
            cv.put(Quote.Columns.FIELD_BODY, quote.getText());
            cv.put(Quote.Columns.FIELD_DATE, quote.getDate());
            cv.put(Quote.Columns.FIELD_INDEX_ON_PAGE, quote.getIndexOnPage());
            cv.put(Quote.Columns.FIELD_PAGE, quote.getPage());
            cv.put(Quote.Columns.FIELD_RATING, quote.getRating());
            cv.put(Quote.Columns.FIELD_LIKED, quote.isLiked() ? 1 : 0);
            mDB.insertWithOnConflict(Quote.Columns.TABLE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public void setQuoteLiked(long quoteId, boolean like) {
        ContentValues cv = new ContentValues();
        cv.put(Quote.Columns.FIELD_LIKED, like);
        mDB.update(Quote.Columns.TABLE, cv, Quote.Columns.FIELD_ID + "=?", new String[]{String.valueOf(quoteId)});
        notifyAboutChange();
    }

    public boolean isQuoteSaved(Quote quote) {
        if (quote == null)
            return false;
        long size = DatabaseUtils.queryNumEntries(mDB, Quote.Columns.TABLE,
                Quote.Columns.FIELD_ID + "=?", new String[] {String.valueOf(quote.getId())});
        return size > 0;
    }

    public boolean isPageSaved(String pageNumber) {
        if (Integer.parseInt(pageNumber) == Constants.PAGE_MAX)
            return false;
        long size = DatabaseUtils.queryNumEntries(mDB, Quote.Columns.TABLE,
                Quote.Columns.FIELD_PAGE + "=?", new String[] {pageNumber});
        return size > 49;
    }

    public void persist(List<Quote> quotes) {
        mDB.beginTransaction();
        try {
            for (Quote quote : quotes) {
                persist(quote);
            }
            mDB.setTransactionSuccessful();
        } finally {
            mDB.endTransaction();
        }
    }

    public void persist(Quote[] quotes) {
        mDB.beginTransaction();
        try {
            for (Quote quote : quotes) {
                persist(quote);
            }
            mDB.setTransactionSuccessful();
        } finally {
            mDB.endTransaction();
        }
    }

    public long getCountOfLoadedQuotes() {
        return DatabaseUtils.queryNumEntries(mDB, Quote.Columns.TABLE, null, null);
    }

    public String compressDbToJson() {
        long start = System.currentTimeMillis();
        String result = null;
        Cursor c = null;
        try {
            c = mDB.query(Quote.Columns.TABLE, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int qIndex = c.getColumnIndex(Quote.Columns.FIELD_BODY);
                int dIndex = c.getColumnIndex(Quote.Columns.FIELD_DATE);
                int idIndex = c.getColumnIndex(Quote.Columns.FIELD_ID);
                int rIndex = c.getColumnIndex(Quote.Columns.FIELD_RATING);

                JSONArray jsonArray = new JSONArray();
                do {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("q", c.getString(qIndex));
                        jsonObject.put("d", c.getString(dIndex));
                        jsonObject.put("#", c.getString(idIndex));
                        jsonObject.put("r", c.getString(rIndex));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                } while (c.moveToNext());
                result = jsonArray.toString();
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        Log.e(DB_NAME,
                "compressed DB to json in: " + String.valueOf(System.currentTimeMillis() - start));
        return result;
    }

    public Cursor fetch(QuoteSelection quoteSelection, int limit) {
        String selection = null;
        String[] args = null;
        String orderBy = null;
        switch (quoteSelection) {
            case LAST:
                orderBy = Quote.Columns.FIELD_ID + " DESC";
                break;
            case RANDOM:
                orderBy = " RANDOM()";
                break;
            case BEST:
                orderBy = Quote.Columns.FIELD_RATING + " DESC";
                break;
            case LIKED:
                selection = Quote.Columns.FIELD_LIKED + " = ?";
                args = new String[] {String.valueOf(1)};
                orderBy = Quote.Columns.FIELD_ID + " DESC";
                break;
        }
        return mDB.query(Quote.Columns.TABLE, null, selection, args, null, null, orderBy,
                String.valueOf(limit));
    }

    public void persist(@NonNull JSONArray quotes) {
        return;
//        long start = System.currentTimeMillis();
//        ContentValues cv = new ContentValues();
//        mDB.beginTransaction();
//        try {
//            for (int i = 0, size = quotes.length(); i < size; i++) {
//                try {
//                    cv.clear();
//                    JSONObject json = (JSONObject) quotes.get(i);
//                    cv.put(Quote.Columns.FIELD_ID, json.getString("#"));
//                    cv.put(Quote.Columns.FIELD_BODY, json.getString("q"));
//                    cv.put(Quote.Columns.FIELD_DATE, json.getString("d"));
//                    cv.put(Quote.Columns.FIELD_RATING, json.getString("r"));
//                    mDB.insertWithOnConflict(Quote.Columns.TABLE, null, cv, SQLiteDatabase
// .CONFLICT_REPLACE);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            mDB.setTransactionSuccessful();
//        } finally {
//            mDB.endTransaction();
//        }
//        Log.e("TAG", "persisted " + quotes.length() + " in: " + String.valueOf(System
// .currentTimeMillis() - start));
    }

    private void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    private void notifyAboutChange() {
        mCtx.getContentResolver().notifyChange(Uri.parse(Constants.URI_QUOTE), null);
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
            if (oldVersion == 1 && newVersion == 2) {
                db.execSQL("ALTER TABLE " + Quote.Columns.TABLE + " ADD COLUMN " + Quote.Columns.FIELD_LIKED + " INTEGER");
            }
        }
    }

}
