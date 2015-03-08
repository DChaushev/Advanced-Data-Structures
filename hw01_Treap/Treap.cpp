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
        if (this->root == root)
            root->parent = 0;
        else root->parent = parent;
        heapify(*root);
    } else if (key < root->key)
        insert(root->left, key, root);
    else if (key > root->key)
        insert(root->right, key, root);
}

void Treap::heapify(TreapNode& node) {
    TreapNode* parent = node.parent;
    if (&node != root && node.priority < parent->priority) {
        if (parent->left == &node) {
            rotate_right(*node.parent);
            heapify(node);
        }
        if (parent->right == &node) {
            rotate_left(*node.parent);
            heapify(node);
        }
    }
}

void Treap::treverse() {

    std::cout << "=============" << std::endl;

    std::queue<TreapNode*> q;
    q.push(root);

    while (!q.empty()) {
        TreapNode* n = q.front();

        q.pop();

        std::cout << n->key << ": " << n->priority << " ";
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
    return containsKey(*root, key);
}

bool Treap::containsKey(TreapNode& root, int key) const {
    if (&root == 0) return false;

    if (root.key == key)
        return true;
    if (key < root.key)
        return containsKey(*root.left, key);
    if (key > root.key)
        return containsKey(*root.right, key);
}

void Treap::rotate_left(TreapNode& node) {

    TreapNode* w = node.right;
    w->parent = node.parent;
    if (w->parent != 0) {
        if (w->parent->left == &node) {
            w->parent->left = w;
        } else {
            w->parent->right = w;
        }
    }
    node.right = w->left;
    if (node.right != 0) {
        node.right->parent = &node;
    }
    node.parent = w;
    w->left = &node;
    if (&node == root) {
        root = w;
        root->parent = 0;
    }
}

void Treap::rotate_right(TreapNode& node) {

    TreapNode* w = node.left;
    if (w != 0) {
        w->parent = node.parent;
        if (w->parent != 0) {
            if (w->parent->left == &node) {
                w->parent->left = w;
            } else {
                w->parent->right = w;
            }
        }
    }
    node.left = w->right;
    if (node.left != 0) {
        node.left->parent = &node;
    }
    node.parent = w;
    w->right = &node;
    if (&node == root) {
        root = w;
        root->parent = 0;
    }
}


