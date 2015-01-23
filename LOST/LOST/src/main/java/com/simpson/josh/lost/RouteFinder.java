package com.simpson.josh.lost;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Josh on 23/01/2015.
 */
public class RouteFinder extends Activity {

    private static int version = 1;
    private static String dbName = "LocationDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);

        SQLiteDatabase myDB = SQLiteDatabase.openDatabase("/assets/LocationDatabase", null, SQLiteDatabase.CREATE_IF_NECESSARY);

        Cursor c = myDB.rawQuery("SELECT LocName FROM Locations", null);

        Spinner start = (Spinner) findViewById(R.id.routeFrom);
        Spinner end = (Spinner) findViewById(R.id.routeTo);

        ArrayList<String> sqlResults = new ArrayList<String>();

        for (int i = 0; i < c.getCount(); i++) {
            sqlResults.add(c.getString(i));
        }

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sqlResults);
        end.setAdapter(spinAdapter);


    }
}
