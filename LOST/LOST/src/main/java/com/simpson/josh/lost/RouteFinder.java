package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Josh on 23/01/2015.
 */
public class RouteFinder extends Activity {

    Spinner start;
    Spinner end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor getLocations = db.getLocNames();

        Log.d("IT WORKED", "FUCKING FINALLY");

        //Initialise spinner
        start = (Spinner) findViewById(R.id.routeFrom);
        end = (Spinner) findViewById(R.id.routeTo);

        //Double adapter because I can
        ArrayList<String> sqlResults = new ArrayList<String>();
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(RouteFinder.this, android.R.layout.simple_spinner_item, sqlResults);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //Like I said, double adapter
        start.setAdapter(spinAdapter);
        end.setAdapter(spinAdapter);

        //Just gonna fill that adapter up here...
        getLocations.moveToFirst();
        do {
            spinAdapter.add(getLocations.getString(0));
        } while (getLocations.moveToNext());
    }

    public void getPath(View view) {
        DiGraph dg = MainActivity.myGraph;
        Dijkstra pathFinder = new Dijkstra(MainActivity.myGraph);

        pathFinder.execute(dg.getNodeFromLocation(start.getSelectedItem().toString()));
        LinkedList<Node> pathList = pathFinder.getPath(dg.getNodeFromLocation(end.getSelectedItem().toString()));

        Log.d("Path List is: ", "" + pathList.size());

        String[] newArray = new String[pathList.size()];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = pathList.get(i).location;
        }

        Intent newIntent = new Intent(this, RouteResults.class);
        newIntent.putExtra("Path", newArray);
        startActivity(newIntent);
    }
}
