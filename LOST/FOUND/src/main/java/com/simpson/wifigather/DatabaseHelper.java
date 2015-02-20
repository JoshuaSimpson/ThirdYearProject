package com.simpson.wifigather;

import android.content.ContentValues;
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

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertLocation(String locName, String MACOne, String MACTwo, String MACThree, int locID)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("LocName", locName);
        cv.put("MACOne", MACOne);
        cv.put("MACTwo", MACTwo);
        cv.put("MACThree", MACThree);
        cv.put("LocID", locID);
        db.insert("LocNode", null, cv);


    }

    public void insertEdge(int id, int startVertex, int endVertex, int weight, String method)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EdgeID", id);
        cv.put("StartVertex", startVertex);
        cv.put("EndVertex", endVertex);
        cv.put("Weight", weight);
        cv.put("Method", method);
    }

    public int returnCount()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor counter = db.rawQuery("SELECT COUNT(*) FROM LocNode", null);
        counter.moveToFirst();
        return counter.getInt(0);
    }
}
