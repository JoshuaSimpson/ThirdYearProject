package com.simpson.wifigather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import org.apache.http.Header;

import java.io.File;

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

    public void insertLocation(String locName, String MACOne, int locID)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("LocName", locName);
        cv.put("MACOne", MACOne);
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
        db.insert("LocEdge", null, cv);
    }

    public int getNodeCount()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor counter = db.rawQuery("SELECT COUNT(*) FROM LocNode", null);
        counter.moveToFirst();
        return counter.getInt(0);
    }

    public int getEdgeCount()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor counter = db.rawQuery("SELECT COUNT(*) FROM LocEdge", null);
        counter.moveToFirst();
        return counter.getInt(0);
    }

    public Cursor getNodeList()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor idList = db.rawQuery("SELECT LocID, LocName FROM LocNode", null);

        return idList;
    }

    public void uploadData() {
        SQLiteDatabase db = getReadableDatabase();

        Log.d("This worked", "Really");
        RequestParams params = new RequestParams();
        try {
            params.put("DB", new File(db.getPath()));
            Log.d("So did the params", "Awesome");
        } catch (Exception f) {
            Log.d("Didn't work", "Dammit");
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(7000);
        client.post("http://178.62.73.203/dbs", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hell yeah", "It worked");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Whoops, post failed", "D:");
                Log.d("We had:", "" + statusCode + error.toString());
            }
        });
    }
}
