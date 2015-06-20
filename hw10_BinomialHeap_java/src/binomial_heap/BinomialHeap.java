/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binomial_heap;

/**
 *
 * @author Dimitar
 */
public class BinomialHeap implements BinomialHeapInterface {

    private Node root;
    private int size;

    public BinomialHeap() {
    }

    //TODO: for testing - delete before submitting
    public BinomialHeap(Node root){
        this.root = root;
    }
    
    public BinomialHeap(Comparable[] elements) {
    }

    @Override
    public Node consolidate(Node leftRoot, int leftSize, Node rightRoot, int rightSize) {
        return null;
    }

    @Override
    public Comparable getMin() {
        if (this.root == null) {
            return null;
        }
        Comparable min = this.root.key;
        Node current = this.root.right;

        while (current != null) {
            if (current.key.compareTo(min) < 0) {
                min = current.key;
            }
            current = current.right;
        }
        return min;
    }

    @Override
    public Node getRoot() {
        return null;
    }

    @Override
    public int getSize() {
        return -1;
    }

    @Override
    public Comparable extractMin() {
        return null;
    }

    @Override
    public void union(BinomialHeapInterface otherHeap) {
    }

    @Override
    public Node push(Comparable newKey) {
        return null;
    }

    @Override
    public void decreaseKey(Node node, Comparable newKey) {
    }

}
