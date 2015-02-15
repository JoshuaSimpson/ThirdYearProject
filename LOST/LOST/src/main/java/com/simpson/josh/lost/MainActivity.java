package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;

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
                do{
                    myGraph.addEdge(Edge.createEdge(getEdges.getInt(0), myGraph.getNodeFromID(getEdges.getInt(1)), myGraph.getNodeFromID(getEdges.getInt(2)), getEdges.getString(4), getEdges.getInt(3)));
                } while (getEdges.moveToNext());

                Log.d("Fucking hell digraphs", "" + myGraph.adjSizeTest());
                Log.d("Digraph test edges", "" + myGraph.getEdgeCount());

                for (int i = 1; i < myGraph.adjSizeTest(); i++) {
                    if (myGraph.getNodeFromID(i) == null) {
                        Log.d("Whoops", "A daisy");
                        Log.d("REALLY COME ON DIGRAPH", "" + myGraph.getAdjacency(myGraph.getNodeFromID(i)).size());
                    } else if (myGraph.getAdjacency(myGraph.getNodeFromID(i)).size() == 0) {
                        Log.d("We have an outlier", "It's node: " + i);
                    }
                }

                Dijkstra dijkstra = new Dijkstra(myGraph);
                dijkstra.execute(myGraph.getNodeFromID(10));

                LinkedList<Node> path = dijkstra.getPath(myGraph.getNodeFromID(50));
                Log.d("Dijkstra got", "" + path.size());

                Iterator<Node> pathIt = path.listIterator();

                while (pathIt.hasNext()) {
                    Log.d("Path step", "" + pathIt.next().id);
                }

            }
        }).run();

    }

    public void whereAmI(View view)
    {
        Intent openView = new Intent(this, WhereAmI.class);
        startActivity(openView);
    }

    public void routeFinder(View view) {
        Intent openView = new Intent(this, RouteFinder.class);
        startActivity(openView);
    }
    }
