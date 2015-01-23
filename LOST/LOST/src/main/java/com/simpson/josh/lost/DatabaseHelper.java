package com.simpson.josh.lost;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Josh on 23/01/2015.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "LocationDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getLocNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"LocName"};
        String sqlTables = "Locations";

        qb.setTables(sqlTables);

        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        c.moveToFirst();
        return c;
    }
}
