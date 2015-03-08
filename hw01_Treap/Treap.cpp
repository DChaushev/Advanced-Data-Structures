/* 
 * File:   Treap.cpp
 * Author: Dimitar
 * 
 * Created on March 7, 2015, 10:32 PM
 */

#include "Treap.h"
#include <queue>
#include <iostream>

Treap::Treap() {
    root = 0;
}

void Treap::insert(int key) {
    insert(root, key, root);
}

void Treap::insert(TreapNode*& root, int key, TreapNode*& parent) {
    if (!root) {
        root = new TreapNode(key);
        root->parent = parent;
    } else if (key < root->key)
        insert(root->left, key, root);
    else if (key > root->key)
        insert(root->right, key, root);
}

void Treap::treverse() {
    std::queue<TreapNode*> q;
    q.push(root);

    while (!q.empty()) {
        TreapNode* n = q.front();
        q.pop();

        std::cout << n->key << ": ";
        if (n->left != 0) {
            std::cout << " left: " << n->left->key;
            q.push(n->left);
        }
        if (n->right != 0) {
            std::cout << " right: " << n->right->key;
            q.push(n->right);
        }

        std::cout << std::endl;
    }
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
        node.parent = node.left->parent;
        node.parent->left = &node;
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
        node.parent = node.right->parent;
        node.parent->right = &node;
    }
    node.right->parent = &node;
}


