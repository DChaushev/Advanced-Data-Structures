package com.fmi.ads.avl;

/**
 * Defines a single node of the binary tree
 *
 * @author Boris Strnadjev
 *
 * @param <T> The type of values stored in the tree.
 */
public class Node<T extends Comparable<T>> {

    public Node<T> parent;
    public Node<T> leftChild, rightChild;
    public int height;
    public T value;

}
