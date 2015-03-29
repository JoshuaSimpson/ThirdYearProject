package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    public static DiGraph myGraph;
    DatabaseHelper db;
    Cursor getLocations;
    Cursor getEdges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPrefs.edit();


        if (!sharedPrefs.contains("FirstTime")) {
            Log.d("Cool, make a thing", "NOW");

            ed.putBoolean("FirstTime", true);
            ed.putBoolean("Location", true);
            ed.putBoolean("Wireless", true);

            Downloader dl = new Downloader();
            dl.execute(this.getFilesDir().toString());
            try {
                //Just delays anything else running until we know the file is downloaded. Hacky, but it works
                dl.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ed.commit();
        } else if (sharedPrefs.contains("FirstTime")) {
            Log.d("Awesome it did the thing", "Well done");
        }

        //Nothing much doing here...
        myGraph = new DiGraph();

        db = new DatabaseHelper(this);
        getLocations = db.getLocNames();
        getEdges = db.getEdges();

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Put the nodes into our DiGraph in another thread so as not to freeze up the main GUI thread
                getLocations.moveToFirst();

                //Test entries at the moment - need to edit SQL entries
                do {
                    String[] macs = {getLocations.getString(1), getLocations.getString(2), getLocations.getString(3)};
                    myGraph.addNode(Node.createNode(getLocations.getInt(4), macs, getLocations.getString(0)));
                } while (getLocations.moveToNext());

                getEdges.moveToFirst();
                do {
                    myGraph.addEdge(Edge.createEdge(getEdges.getInt(0), myGraph.getNodeFromID(getEdges.getInt(1)), myGraph.getNodeFromID(getEdges.getInt(2)), getEdges.getString(4), getEdges.getInt(3)));
                } while (getEdges.moveToNext());

                for (int i = 1; i < myGraph.adjSizeTest(); i++) {
                    if (myGraph.getNodeFromID(i) == null) {
                        Log.d("Whoops", "A daisy");
                        Log.d("REALLY COME ON DIGRAPH", "" + myGraph.getAdjacency(myGraph.getNodeFromID(i)).size());
                    } else if (myGraph.getAdjacency(myGraph.getNodeFromID(i)).size() == 0) {
                        Log.d("We have an outlier", "It's node: " + i);
                    }
                }

                Log.d("WHAT THE SHIT", "" + myGraph.getEdgeCount());

            }
        }).run();

    }

    public void whereAmI(View view) {
        Intent openView = new Intent(this, WhereAmI.class);
        startActivity(openView);
    }

    public void routeFinder(View view) {
        Intent openView = new Intent(this, RouteFinder.class);
        startActivity(openView);
    }

    public void settings(View view) {
        Intent openView = new Intent(this, settings.class);
        startActivity(openView);
    }
}
