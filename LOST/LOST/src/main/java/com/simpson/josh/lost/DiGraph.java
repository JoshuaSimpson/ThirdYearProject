package com.simpson.josh.lost;

import android.util.Log;

import java.util.*;

/**
 * Created by Josh on 23/01/2015.
 */
public class DiGraph {

    public static Map<Node, HashMap<Node, Edge>> adjacency;
    public static Map<Integer, Node> nodes;
    public static List<Edge> edges;
    public static Map<Integer, Node> nodeIDList;
    public static Map<String, Node> nodeLocationList;
    public static Map<String, Node> nodeMAC;

    public DiGraph() {
        //I get the feeling this needs filling..
        //Implement .equals and implement hash codes.
        adjacency = new HashMap<Node, HashMap<Node, Edge>>();
        nodes = new HashMap();
        edges = new ArrayList<Edge>();
        nodeIDList = new HashMap<Integer, Node>();
        nodeLocationList = new HashMap<String, Node>();
        nodeMAC = new HashMap<String, Node>();
    }

    public void addNode(Node newNode) {
        nodes.put(newNode.id, newNode);
        adjacency.put(newNode, new HashMap<Node, Edge>());
        nodeIDList.put(newNode.id, newNode);
        nodeLocationList.put(newNode.location, newNode);
        nodeMAC.put(newNode.mac, newNode);
    }

    public void addEdge(Edge newEdge) {
        //This might be a little complicated / expensive, come back to this.
        if (nodes.containsValue(newEdge.startNode) && nodes.containsValue(newEdge.endNode)) {
            edges.add(newEdge);
            edges.size();
            adjacency.get(newEdge.startNode).put(newEdge.endNode, newEdge);
            Log.d("Node: " + newEdge.startNode.id + " is being connected to: " + newEdge.endNode.id, "Edge ID: " + newEdge.edgeID);
        } else {

        }
    }

    public boolean containsMAC(String mac) {
        return nodeMAC.containsKey(mac);
    }

    public Node getNodeFromWiFi(String M1, String M2, String M3) {
        return nodes.get(M1 + M2 + M3);
    }

    public Node getNodeFromID(int id) {
        //Need this to be able to associate new  edges properly in MainActivity
        return nodeIDList.get(id);
    }

    public String getLocFromMac(String mac) {
        String nopeOut = "";
        try {
            nopeOut = nodeMAC.get(mac).location;
        } catch (Exception e) {
            nopeOut = "Seriously just nope it all";
        }
        return nopeOut;
    }

    //Test method
    public void printMACS() {
        Set<String> maclist = nodeMAC.keySet();
        for (String s : maclist) {
            Log.d("MAC IS: ", s);
        }
    }

    public Node getNodeFromLocation(String location) {
        return nodeLocationList.get(location);
    }

    public HashMap<Node, Edge> getAdjacency(Node sourceNode) {
        HashMap<Node, Edge> tempMap = adjacency.get(sourceNode);
        return tempMap;
    }

    public int adjSizeTest() {
        return adjacency.size();
    }

    public int getNodeCount() {
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
