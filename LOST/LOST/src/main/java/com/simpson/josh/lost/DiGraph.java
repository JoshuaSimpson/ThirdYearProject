package com.simpson.josh.lost;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Josh on 23/01/2015.
 */
public class DiGraph {

    Map<Node, HashMap<Node, Edge>> adjacency;
    Map<String, Node> nodes;
    List<Edge> edges;

    public DiGraph() {
        //I get the feeling this needs filling..
        //Implement .equals and implement hash codes.
        adjacency = new HashMap<Node, HashMap<Node, Edge>>();
        this.nodes = new HashMap();
        this.edges = new ArrayList<Edge>();
    }

    public void addNode(Node newNode) {
        Log.d("FUCKIN' FILLER", "SERIOUSLY JUST NEED TO CLEAN THIS UP");
    }

    public void addEdge(Edge newEdge) {
        //This might be a little complicated / expensive, come back to this.
        if(nodes.containsValue(newEdge.startNode) && nodes.containsValue(newEdge.endNode))
        {
            edges.add(newEdge);
            adjacency.get(newEdge.startNode).put(newEdge.endNode, newEdge);

        }
        else{

        }
    }

    public int getNodeCount()
    {
        return this.nodes.size();
    }
}
