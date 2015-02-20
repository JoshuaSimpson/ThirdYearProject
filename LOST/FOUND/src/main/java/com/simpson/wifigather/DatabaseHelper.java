package com.simpson.wifigather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by josh on 20/02/15.
 */
public class DatabaseHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "LOSTANDFOUND";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase.CursorFactory cf;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cf = new

    }

    public Cursor insertLocation(String name)
    {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/FOUND", null);

        return db.query(
    }
}
