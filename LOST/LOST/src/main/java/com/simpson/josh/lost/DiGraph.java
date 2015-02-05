package com.simpson.josh.lost;

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
        String mac = newNode.macs[0] + newNode.macs[1] + newNode.macs[2];
        nodes.put(mac, newNode);
        adjacency.put(newNode, new HashMap<Node, Edge>());
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

    public HashMap<Node, Edge> getAdjacency(Node sourceNode) {

        HashMap<Node, Edge> tempMap = adjacency.get(sourceNode);
        return tempMap;
    }

    public int getNodeCount()
    {
        return this.nodes.size();
    }

    public class NoConnectionException extends Exception {
        public NoConnectionException() {
        }

        //Constructor that accepts a message
        public NoConnectionException(String message) {
            super(message);
        }
    }
}
