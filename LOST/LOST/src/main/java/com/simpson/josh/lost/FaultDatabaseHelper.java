package com.simpson.josh.lost;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Josh on 09/04/2015.
 */
public class FaultDatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "FaultList";
    private static final int DATABASE_VERSION = 1;

    public FaultDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getFaults() {
        SQLiteDatabase db = getReadableDatabase();

        return null;
    }
}
