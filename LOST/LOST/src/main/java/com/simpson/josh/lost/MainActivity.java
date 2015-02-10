package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    DiGraph myGraph;
    DatabaseHelper db;
    Cursor getLocations;
    Cursor getEdges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nothing much doing here...
        myGraph = new DiGraph();

        db = new DatabaseHelper(this);
        getLocations = db.getLocNames();

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Put the nodes into our DiGraph in another thread so as not to freeze up the main GUI thread
                Log.d("IT WORKED", "FUCKING FINALLY");
                getLocations.moveToFirst();

                //Test entries at the moment - need to edit SQL entries
                do {
                    String[] macs = {getLocations.getString(1), getLocations.getString(2), getLocations.getString(3)};
                    myGraph.addNode(Node.createNode(getLocations.getInt(4), macs, getLocations.getString(0)));
                } while (getLocations.moveToNext());

                getEdges = db.getEdges();

                getEdges.moveToFirst();
                do{
                    myGraph.addEdge(Edge.createEdge(getEdges.getInt(0), myGraph.getNodeFromID(getEdges.getInt(1)), myGraph.getNodeFromID(getEdges.getInt(2)), getEdges.getString(3), getEdges.getInt(4)));
                    Log.d("Number of Edges", "" + myGraph.getEdgeCount());
                } while (getEdges.moveToNext());


                /*for (int i = 0; i < 10; i++) {

                    myGraph.addNode(Node.createNode(i, macs, "location"));
                    Log.d("Creating and adding", "SIR");
                }*/
            }
        }).run();

        for (int i = 0; i < myGraph.nodes.size(); i++) {
            Log.d("Try hard:", myGraph.nodes.entrySet().iterator().next().toString());

        }
        Log.d("Graph Size", " " + myGraph.getNodeCount());
    }

    public void whereAmI(View view)
    {
        Log.d("STUFF HERE", getFilesDir().getPath().toString());
        Intent openView = new Intent(this, WhereAmI.class);
        startActivity(openView);
    }

    public void routeFinder(View view) {
        Intent openView = new Intent(this, RouteFinder.class);
        startActivity(openView);
    }
    }
