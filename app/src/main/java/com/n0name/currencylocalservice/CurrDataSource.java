package com.n0name.currencylocalservice;

/**
 * Created by N0Name on 19-Mar-16.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CurrDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.ROW_ID, DatabaseHelper.CURRENCY,
            DatabaseHelper.EXCHANGE};

    public CurrDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

     public Cursor getCursor() {
        List<Currency> currs = new ArrayList<Currency>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        return cursor;
    }

    public double getCurrencyByID(int id) {
        double result=0;
        Cursor cursor=getCursor();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(0)==id) result=cursor.getDouble(2);
            Log.i("message", "" + cursor.getDouble(2));
            Log.i("message", "" + cursor.getInt(0));
            cursor.moveToNext();
        }

        Log.i("message", "" + result);
        System.out.println("result:"+result);
        return  result;
    }




}
