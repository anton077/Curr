package com.n0name.currencylocalservice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by N0Name on 20-Feb-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "currency_db";
    public static final String ROW_ID = "_id";
    public static final String CURRENCY = "currency_name";
    public static final String EXCHANGE = "value";
    public static final String TABLE_NAME ="currencies";
    private Cursor currCursor = null;



    public static final String SQL_CREATE_ENTRIES =
            ("CREATE TABLE " +TABLE_NAME+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + CURRENCY + " TEXT, " + EXCHANGE + " REAL)");
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        Log.i("message", "DatabaseHelper constructor "+TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.i("message", "SQLiteDatabase onCreated ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);}




}
