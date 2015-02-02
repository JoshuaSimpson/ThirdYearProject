package com.simpson.josh.lost;

/**
 * Created by Josh on 02/02/2015.
 */
public class Node {
    int id;
    String[] macs;
    String location;

    public Node(int id, String[] macs, String location) {
        this.id = id;
        this.macs = macs;
        this.location = location;
    }

    public static Node createNode(int id, String[] macs, String location) {
        Node node = new Node(id, macs, location);
        node.id = id;
        node.macs = macs;
        node.location = location;

        return node;
    }
}