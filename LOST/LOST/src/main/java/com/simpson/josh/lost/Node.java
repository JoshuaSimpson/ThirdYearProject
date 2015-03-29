package com.simpson.josh.lost;

/**
 * Created by Josh on 02/02/2015.
 */
public class Node {
    int id;
    String mac;
    String location;

    public Node(int id, String mac, String location) {
        this.id = id;
        this.mac = mac;
        this.location = location;
    }

    public static Node createNode(int id, String mac, String location) {
        Node node = new Node(id, mac, location);
        node.id = id;
        node.mac = mac;
        node.location = location;

        return node;
    }
}