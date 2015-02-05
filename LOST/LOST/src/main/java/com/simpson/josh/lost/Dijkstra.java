package com.simpson.josh.lost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josh on 04/02/2015.
 */
public class Dijkstra {

    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;
    Set<Node> visitedNodes;
    Set<Node> unVisitedNodes;


    public Dijkstra(DiGraph graph) {
        this.nodeList = new ArrayList<Node>(graph.nodes.values());
        this.edgeList = new ArrayList<Edge>(graph.edges);
    }

    public void execute(Node sourceNode) {
        visitedNodes = new HashSet<Node>();
        unVisitedNodes = new HashSet<Node>();


    }

    public void getMinimalDistances(Node sourceNode) {

    }

}
