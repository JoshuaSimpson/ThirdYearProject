package com.simpson.josh.lost;

import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by Josh on 23/01/2015.
 */
public class DiGraph {

    Map<Node, Map<Edge, Node>> adjacency;
    List<Node> nodes;
    List<Edge> edges;

    public DiGraph() {
        //I get the feeling this needs filling..
    }

    public void addNode(Node newNode) {
        this.nodes.add(newNode);
    }

    public void addEdge(Edge newEdge) {
        //This might be a little complicated / expensive, come back to this.
        if (nodes.contains(newEdge.startNode) && nodes.contains(newEdge.endNode)) {
            edges.add(newEdge);
            Map<Edge, Node> adjList = adjacency.get(newEdge.startNode);
            adjList.put(newEdge, newEdge.endNode);
            adjacency.put(newEdge.startNode, adjList);
        } else {
            Log.d("Nooope", "Need both nodes in first...");
        }
    }

    public class Node {
        int id;
        String[] macs;
        String location;

        public Node(int id, String[] macs, String location) {
            this.id = id;
            this.macs = macs;
            this.location = location;
        }

        public Node createNode(int id, String[] macs, String location) {
            Node node = new Node(id, macs, location);
            node.id = id;
            node.macs = macs;
            node.location = location;

            return node;
        }
    }

    public class Edge {
        int edgeID;
        Node startNode;
        Node endNode;
        String travelType;
        int weight;

        public Edge(int edgeID, Node startNode, Node endNode, String travelType, int weight) {
            this.edgeID = edgeID;
            this.startNode = startNode;
            this.endNode = endNode;
            this.travelType = travelType;
            this.weight = weight;
        }

        public Edge createEdge(int edgeID, Node startNode, Node endNode, String travelType, int weight) {
            Edge edge = new Edge(edgeID, startNode, endNode, travelType, weight);
            edge.edgeID = edgeID;
            edge.startNode = startNode;
            edge.endNode = endNode;
            edge.travelType = travelType;
            edge.weight = weight;

            return edge;
        }
    }
}
