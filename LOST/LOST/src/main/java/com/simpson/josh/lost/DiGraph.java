package com.simpson.josh.lost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Josh on 23/01/2015.
 */
public class DiGraph {

    public static Map<Node, HashMap<Node, Edge>> adjacency;
    public static Map<String, Node> nodes;
    public static List<Edge> edges;
    public static Map<Integer, Node> nodeIDList;

    public DiGraph() {
        //I get the feeling this needs filling..
        //Implement .equals and implement hash codes.
        adjacency = new HashMap<Node, HashMap<Node, Edge>>();
        nodes = new HashMap();
        edges = new ArrayList<Edge>();
        nodeIDList = new HashMap<Integer, Node>();
    }

    public void addNode(Node newNode) {
        String mac = newNode.macs[0] + newNode.macs[1] + newNode.macs[2];
        nodes.put(mac, newNode);
        adjacency.put(newNode, new HashMap<Node, Edge>());
        nodeIDList.put(newNode.id, newNode);
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

    public Node getNodeFromWiFi(String M1, String M2, String M3)
    {
       return nodes.get(M1 + M2 + M3);
    }

    public Node getNodeFromID(int id)
    {
        //Need this to be able to associate new edges properly in MainActivity
        return nodeIDList.get(id);
    }

    public HashMap<Node, Edge> getAdjacency(Node sourceNode) {

        HashMap<Node, Edge> tempMap = adjacency.get(sourceNode);
        return tempMap;
    }

    public int adjSizeTest() {
        return adjacency.size();
    }

    public int getNodeCount()
    {
        return nodes.size();
    }

    public int getEdgeCount() {
        return edges.size();
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
