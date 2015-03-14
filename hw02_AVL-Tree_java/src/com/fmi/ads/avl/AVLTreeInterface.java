package com.fmi.ads.avl;

/**
 * Defines the interface for the AVL tree you need to implement.
 * 
 * @author Boris Strandjev
 *
 * @param <T> The type of the values stored in the tree
 */
public abstract class AVLTreeInterface<T extends Comparable<T>> {
	protected Node<T> root;
	protected int size;

	/* Returns the number of nodes in the tree. 0 in case of empty tree. */
	abstract public int getSize();

	/* Inserts the given value as node in the tree. */
	abstract public void insertNode(T value);

	/*
	 * Finds a node with value equal to the given parameter. Returns NULL if no
	 * such is found.
	 */
	abstract Node<T> findNode(T value);

	/*
	 * Deletes a node with the given value from the tree. If the value is not
	 * found in the tree no modification to the tree should be made.
	 */
	abstract public void deleteNode(T value);
};
