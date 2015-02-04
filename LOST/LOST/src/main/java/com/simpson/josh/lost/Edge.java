package com.simpson.josh.lost;

/**
 * Created by Josh on 02/02/2015.
 */
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

    public int weight() {
        return this.weight;
    }

    public String travelType() {
        return this.travelType;
    }
}