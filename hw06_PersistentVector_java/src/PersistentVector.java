
import java.util.Arrays;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dimitar
 * @param <T>
 */
public class PersistentVector<T> {

    private final int BITS = 1;
    private final int WIDTH = 1 << BITS;
    private final int MASK = WIDTH - 1;
    private int shift = 0;
    int depth = 0;
    private int size = 0;
    Node root = null;
    private Node tail = null;

    public PersistentVector() {
    }

    private PersistentVector(PersistentVector<T> other) {
    }

    /**
     * Returns the value of the element at position {@code index}.
     *
     * @param index
     * @return
     */
    public T get(int index) {
        return null;
    }

    /**
     * Returns a new vector with the element at position {@code index} replaced
     * by {@code value}.
     *
     * @param index
     * @param value
     * @return
     */
    public PersistentVector<T> update(int index, T value) {
        return null;
    }

    /**
     * Returns a new vector with {@code value} appended at the end.
     *
     * @param value
     * @return
     */
    public PersistentVector append(T value) {

        if (root == null) {
            root = new Node();

            tail = new Node();
            tail.setLeaf(true);
            tail.append(value);

            root.append(tail);
            this.size++;
            this.depth++;
            updateShift();

        } else {

            if (tail.hasSpace()) {
                tail.append(value);
                this.size++;
            } else {
                Node parent = lookUp();
                if (parent.hasSpace()) {
                    tail = new Node();
                    tail.setLeaf(true);
                    tail.append(value);
                    parent.append(tail);
                    this.size++;
                } else {
                    Node newRoot = new Node();
                    newRoot.append(root);
                    this.root = newRoot;
                    this.depth++;
                    updateShift();

                    Node current = this.root;
                    for (int i = 0; i < depth; i++) {
                        Node n = new Node();
                        current.append(n);
                        current = n;
                    }
                    this.tail = current;
                    this.tail.append(value);
                    this.size++;
                }
            }
        }
        return null;
    }

    public Node lookUp() {
        Node node = this.root;
        for (int level = this.shift; level > 0; level -= BITS) {
            Node decoy = (Node) node.nodes[((size >>> level) & MASK)];
            if (decoy == null) {
                return node;
            } else {
                node = decoy;
            }
        }
        return node;
    }

    public Object lookUp(int index) {
        Node node = this.root;
        for (int level = this.shift; level > 0; level -= BITS) {
            node = (Node) node.nodes[(index >>> level) & MASK];
        }
        return (Object) node.nodes[index & MASK];
    }

    /**
     * Returns a new vector that's the same as this one but without the last
     * element.
     *
     * @return
     */
    public PersistentVector pop() {
        return null;
    }

    public int size() {
        return this.size;
    }

    private void updateShift() {
        this.shift = BITS * (depth - 1);
    }

    private class Node {

        private final Object[] nodes;
        private int size;
        private boolean leaf;

        public Node() {
            this.size = 0;
            this.nodes = new Object[WIDTH];
            this.leaf = false;
        }

        public Node(Node other) {
            this();
            System.arraycopy(other.nodes, 0, this.nodes, 0, other.size);
            this.size = other.size;
            this.leaf = other.leaf;
        }

        public void append(Object x) {
            if (this.hasSpace()) {
                this.nodes[size++] = x;
            } else {
                throw new ArrayIndexOutOfBoundsException("This node is full!");
            }
        }

        public boolean hasSpace() {
            return this.size < WIDTH;
        }

        @Override
        public String toString() {
            return Arrays.toString(this.nodes);
        }

        public int getSize() {
            return size;
        }

        public T get(int index) {
            if (index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException();
            }
            return (T) nodes[index];
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public boolean isLeaf() {
            return leaf;
        }

    }

}
