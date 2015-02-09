package com.simpson.josh.lost;

import java.util.*;

/**
 * Created by Josh on 04/02/2015.
 */
public class Dijkstra {

    DiGraph dg;
    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;
    Set<Node> visitedNodes;
    Set<Node> unVisitedNodes;
    Map<Node, Integer> distance;


    public Dijkstra(DiGraph graph) {
        this.nodeList = new ArrayList<Node>(graph.nodes.values());
        this.edgeList = new ArrayList<Edge>(graph.edges);
        this.dg = graph;
    }

    public void execute(Node sourceNode) {
        visitedNodes = new HashSet<Node>();
        unVisitedNodes = new HashSet<Node>();


    }

    private Node getMinimum(Set<Node> nodes)
    {
        Node min = null;
        for( Node n : nodes)
        {
            if(min == null)
            {
                min = n;
            }
            else{
                if(getShortestDistance(n) < getShortestDistance(min))
                {
                    min = n;
                }
            }
        }

        return min;
    }

    public List<Node> getNeighbours(Node sourceNode) {
        List<Node> neighbours = new ArrayList<Node>(dg.getAdjacency(sourceNode).keySet());
        // Go through edgelist,remove any visited nodes

        for( Node n : neighbours)
        {
            if(visitedNodes.contains(n))
            {
                neighbours.remove(n);
            }
        }

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

    public int getShortestDistance(Node destinationNode)
    {
        Integer d = distance.get(destinationNode);

        if(d == null)
        {
            return 9999;
        }
        else
        {
            return d;
        }
    }
}
