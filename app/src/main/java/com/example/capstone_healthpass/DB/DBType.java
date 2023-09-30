package com.example.capstone_healthpass.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBType {
    private DataBaseHelper sql;
    private SQLiteDatabase db;
    public DBType(Context context) {
        sql = new DataBaseHelper(context);
    }
    public Cursor selectRawQuery(String query)
    {
        db = sql.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor validRawQuery(String query, String area)
    {
        db = sql.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{area});
        return cursor;
    }
}
