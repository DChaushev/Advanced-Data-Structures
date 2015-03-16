/* 
 * File:   Treap.h
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:32 PM
 */

#ifndef TREAP_H
#define	TREAP_H

#include "TreapNode.h"

class Treap {
public:
    Treap();
    ~Treap();

    /**
     * Inserts a node in the treap with the provided @key
     * 
     * @param key: key to be inserted
     */
    void insert(int key);

    /**
     * Removes the node from the treap with the provided @key
     * 
     * @param key: key to be removed
     */
    void remove(int key);

    /**
     * Checks whether a given @key is already in the treap
     * 
     * @param key: key to be searched for
     * @return true, if the key is in the treap, and false, otherwise
     */
    bool containsKey(int key) const;

    /**
     * For each node prints out his key, his priority and
     * his left and right children (if any).
     * 
     * This method is used mainly for testing.
     */
    void treverse();


private:

    /**
     * This is the starting point of our Treap - it's root.
     */
    TreapNode* root;

    /**
     * The name says it all:
     * rotates the given node to the left.
     * 
     * @param node
     */
    void rotate_left(TreapNode& node);

    /**
     * Rotates the given node to the right.
     * 
     * @param node
     */
    void rotate_right(TreapNode& node);

    /**
     * Comes from the famous heap function's name.
     * After inserting new node, we call this function, to 
     * arrange the nodes by their priorities.
     * 
     * @param The newly inserted node.
     */
    void heapify(TreapNode& node);

    /**
     * Binary searches for node with given key.
     * @see containsKey
     * It is the same, only that this returns the node, if
     * such exists.
     * 
     * @param key
     * @return 
     */
    TreapNode* findNode(const int key) const;

    Treap& operator=(const Treap&); // Do not implement me!
    Treap(const Treap&); // Do not implement me!
};

#endif	/* TREAP_H */

