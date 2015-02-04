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
                int i = 0;
                do {
                    String[] macs = {"Stuff" + i, "Stuff" + i, "Stuff" + i};
                    myGraph.addNode(Node.createNode(i, macs, getLocations.getString(0)));
                    i++;
                    Log.d("Doing stuff:", "" + i);

                } while (getLocations.moveToNext());


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
