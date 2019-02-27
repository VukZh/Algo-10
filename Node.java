package rbtreeapp;

public class Node {
    
    int key;
    boolean isRed;
    Node left;
    Node right;
    Node parent;

    Node(int k, Node prnt) {
        key = k;
        parent = prnt;
        isRed = true;
    }
    
    Node(int k) {
        key = k;
        parent = left = right = null;
        isRed = true;
    }
    
}
