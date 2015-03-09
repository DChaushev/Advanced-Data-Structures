/* 
 * File:   Treap.cpp
 * Author: Dimitar
 * 
 * Created on March 7, 2015, 10:32 PM
 */

#include "Treap.h"
#include <queue>
#include <stack>
#include <iostream>

Treap::Treap() {
    root = 0;
}

Treap::~Treap() {
    std::cout << "\ndeleting the treap..." << std::endl;
    int cnt = 0;
    std::queue<TreapNode*> s;
    s.push(root);

    while (!s.empty()) {
        TreapNode* n = s.front();
        s.pop();
        if (n->left != 0)
            s.push(n->left);
        if (n->right != 0)
            s.push(n->right);

        delete(n);
        cnt++;
    }
    std::cout << "done. " << cnt << " nodes deleted." << std::endl;
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
    TreapNode* node = findNode(*root, key);
    if (node != 0) {
        while (!(node->left == 0 && node->right == 0)) {
            if (node->left == 0) {
                rotate_left(*node);
            } else if (node->right == 0) {
                rotate_right(*node);
            } else if (node->left->priority < node->right->priority) {
                rotate_right(*node);
            } else {
                rotate_left(*node);
            }
            if (root == node) {
                root = node->parent;
            }
        }

        if (node->parent->left && node == node->parent->left)
            node->parent->left = 0;
        if (node->parent->right && node == node->parent->right)
            node->parent->right = 0;

        delete(node);
    }
}

TreapNode* Treap::findNode(TreapNode &root, int key) {
    if (&root == 0)
        return 0;

    if (root.key == key)
        return &root;
    if (key < root.key)
        return findNode(*root.left, key);
    if (key > root.key)
        return findNode(*root.right, key);
}

bool Treap::containsKey(int key) const {
    return containsKey(*root, key);
}

bool Treap::containsKey(const TreapNode& root, const int key) const {
    if (&root == 0) return false;

    if (root.key == key)
        return true;
    if (key < root.key)
        return containsKey(*root.left, key);
    if (key > root.key)
        return containsKey(*root.right, key);
}

void Treap::rotate_left(TreapNode& node) {

    TreapNode* n = node.right;
    n->parent = node.parent;
    if (n->parent != 0) {
        if (n->parent->left == &node) {
            n->parent->left = n;
        } else {
            n->parent->right = n;
        }
    }
    node.right = n->left;
    if (node.right != 0) {
        node.right->parent = &node;
    }
    node.parent = n;
    n->left = &node;
    if (&node == root) {
        root = n;
        root->parent = 0;
    }
}

void Treap::rotate_right(TreapNode& node) {

    TreapNode* n = node.left;
    if (n != 0) {
        n->parent = node.parent;
        if (n->parent != 0) {
            if (n->parent->left == &node) {
                n->parent->left = n;
            } else {
                n->parent->right = n;
            }
        }
    }
    node.left = n->right;
    if (node.left != 0) {
        node.left->parent = &node;
    }
    node.parent = n;
    n->right = &node;
    if (&node == root) {
        root = n;
        root->parent = 0;
    }
}


