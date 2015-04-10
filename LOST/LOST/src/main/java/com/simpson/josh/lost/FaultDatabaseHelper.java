package com.simpson.josh.lost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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

        return db.query("Faults", new String[]{"MAC", "Location", "Time", "Notes"}, null, null, null, null, null);
    }

    public void insertFault(String mac, String location, String time, String notes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("MAC", mac);
        cv.put("Location", location);
        cv.put("Time", time);
        cv.put("Notes", notes);

        db.insert("Faults", null, cv);

        db.close();
    }

    public void clearFaults() {
        SQLiteDatabase db = getWritableDatabase();

        int i = db.delete("Faults", null, null);

        Log.d("DB Delete:", "" + i);
        db.close();
    }

    public boolean isEmpty() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Faults", null);
        cursor.moveToFirst();
        return cursor.getInt(0) == 0;
    }
}
