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
    public BinomialHeap(Node root) {
        this.root = root;
    }

    public BinomialHeap(Comparable[] elements) {
    }

    @Override
    public Node consolidate(Node leftRoot, int leftSize, Node rightRoot, int rightSize) {
        if (leftRoot == null) {
            return rightRoot;
        }
        if (rightRoot == null) {
            return leftRoot;
        }

        String leftRepresentation = Integer.toBinaryString(leftSize);
        String rightRepresentation = Integer.toBinaryString(rightSize);

        if (leftRepresentation.length() > rightRepresentation.length()) {
            int diff = leftRepresentation.length() - rightRepresentation.length();
            rightRepresentation = String.format("%0" + diff + "d" + rightRepresentation, 0);
        }
        if (rightRepresentation.length() > leftRepresentation.length()) {
            int diff = rightRepresentation.length() - leftRepresentation.length();
            leftRepresentation = String.format("%0" + diff + "d" + leftRepresentation, 0);
        }

        System.out.println(leftRepresentation);
        System.out.println(rightRepresentation);

        Node current = new Node();
        Node newRoot = current;
        for (int i = leftRepresentation.length() - 1; i >= 0; i--) {
            if (leftRepresentation.charAt(i) == '1') {
                if (leftRoot != null) {
                    current.right = leftRoot;
                    leftRoot = leftRoot.right;
                    current = current.right;
                }
            }
            if (rightRepresentation.charAt(i) == '1') {
                if (rightRoot != null) {
                    current.right = rightRoot;
                    rightRoot = rightRoot.right;
                    current = current.right;
                }
            }
        }
        return newRoot.right;
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
        return this.root;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Comparable extractMin() {
        return null;
    }

    @Override
    public void union(BinomialHeapInterface otherHeap) {
        this.root = consolidate(this.root, this.size, otherHeap.getRoot(), otherHeap.getSize());
    }

    @Override
    public Node push(Comparable newKey) {
        return null;
    }

    @Override
    public void decreaseKey(Node node, Comparable newKey) {
    }

}
