package com.simpson.josh.lost;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Josh on 23/01/2015.
 */
public class RouteFinder extends Activity {

    private static int version = 1;
    private static String dbName = "databases/LocationDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor getLocations = db.getLocNames();

        Log.d("IT WORKED", "FUCKING FINALLY");

        Spinner start = (Spinner) findViewById(R.id.routeFrom);
        Spinner end = (Spinner) findViewById(R.id.routeTo);

        ArrayList<String> sqlResults = new ArrayList<String>();


        do {
            Log.d("Stuff", getLocations.getString(0).toString());
        } while (getLocations.moveToNext());


        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sqlResults);
        start.setAdapter(spinAdapter);

    }
}
