package com.simpson.josh.lost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Josh on 04/02/2015.
 */
public class Dijkstra {

    DiGraph dg;
    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;
    Set<Node> visitedNodes;
    Set<Node> unVisitedNodes;


    public Dijkstra(DiGraph graph) {
        this.nodeList = new ArrayList<Node>(graph.nodes.values());
        this.edgeList = new ArrayList<Edge>(graph.edges);
        this.dg = graph;
    }

    public void execute(Node sourceNode) {
        visitedNodes = new HashSet<Node>();
        unVisitedNodes = new HashSet<Node>();


    }

    public List<Node> getNeighbours(Node sourceNode) {
        List<Node> neighbours = new ArrayList<Node>(dg.getAdjacency(sourceNode).keySet());
        // Go through edgelist, add any unvisited nodes to nodelist


        return neighbours;

    }

    public boolean isVisited(Node node) {
        return visitedNodes.contains(node);
    }

    public int getDistance(Node sourceNode, Node targetNode)
    {
        int weight = 1;
        for(Edge e : edgeList)
        {
            // Need to go back to this for efficiency - breaking the loop when it finds a result
            // stops the wasting time continuing the loop for now
            if (e.startNode == sourceNode && e.endNode == targetNode)
            {
                weight = e.weight;
                break;
            }
        }
        return weight;
    }



}
