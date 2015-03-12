/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dimitar
 */
import java.util.Random;

public interface Treap {

    static final Random generator = new Random();
    /**
     * Data type which represents a node for a Treap data structure
     *
     */
    public static class TreapNode {

        int key; // key provided by user
        float priority; // node's generated priority
        TreapNode left; // pointer for the left subtree
        TreapNode right; // pointer for the right subtree
        TreapNode parent;

        TreapNode(int key) {
            this.key = key;
            priority = generator.nextFloat();
            left = null;
            right = null;
            parent = null;
        }
    };

    /**
     * Inserts a node in the treap with the provided @key
     *
     * @param key: key to be inserted
     */
    public void insert(int key);

    /**
     * Removes the node from the treap with the provided @key
     *
     * @param key: key to be removed
     */
    public void remove(int key);

    /**
     * Checks whether a given @key is already in the treap
     *
     * @param key: key to be searched for
     * @return true, if the key is in the treap, and false, otherwise
     */
    public boolean containsKey(int key);
    
    @Override
    public String toString();
}
