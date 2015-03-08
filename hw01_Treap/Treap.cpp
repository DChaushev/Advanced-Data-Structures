/* 
 * File:   Treap.cpp
 * Author: Dimitar
 * 
 * Created on March 7, 2015, 10:32 PM
 */

#include "Treap.h"

Treap::Treap() {
    root = 0;
}

void Treap::insert(int key) {
    //TODO
}

void Treap::remove(int key) {
    //TODO
}

bool Treap::containsKey(int key) const {
    //TODO
}

void Treap::rotate_left(TreapNode& node) {
    TreapNode* temp = node.left;
    node.left = node.parent;
    node.left->right = temp;

    if (node.parent == root) {
        root = &node;
    } else {
        temp = node.parent->parent;
        node.parent = temp;
    }
    node.left->parent = &node;
}

void Treap::rotate_right(TreapNode& node) {
    TreapNode* temp = node.right;
    node.right = node.parent;
    node.right->left = temp;

    if (node.parent == root) {
        root = &node;
    } else {
        temp = node.parent->parent;
        node.parent = temp;
    }
    node.right->parent = &node;
}


