package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

        Intent thisIntent = getIntent();
        start.setSelection(thisIntent.getIntExtra("Location", 0));
    }

    public void getPath(View view) {
        if (start.getSelectedItem().toString() == end.getSelectedItem().toString()) {
            Toast.makeText(this, "You're already there!", Toast.LENGTH_SHORT).show();
        } else {

            DiGraph dg = MainActivity.myGraph;
            Dijkstra pathFinder = new Dijkstra(MainActivity.myGraph);


            pathFinder.execute(dg.getNodeFromID(start.getSelectedItemPosition() + 1));
            LinkedList<Node> pathList = pathFinder.getPath(dg.getNodeFromID(end.getSelectedItemPosition() + 1));

            if (pathList.size() == 0) {
                Toast.makeText(this, "Could not find path", Toast.LENGTH_SHORT).show();
            } else {
                String[] newArray = new String[pathList.size()];
                for (int i = 0; i < newArray.length; i++) {
                    newArray[i] = pathList.get(i).location;
                }

                Intent newIntent = new Intent(this, RouteResults.class);
                newIntent.putExtra("Path", newArray);
                startActivity(newIntent);
            }
        }
    }
}
