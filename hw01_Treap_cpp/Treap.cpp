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
    std::queue<TreapNode*> s;
    if (root != 0)
        s.push(root);

    while (!s.empty()) {
        TreapNode* n = s.front();
        s.pop();
        if (n->left != 0)
            s.push(n->left);
        if (n->right != 0)
            s.push(n->right);

        delete(n);
    }
}

void Treap::insert(int key) {
    TreapNode* parent = 0;
    TreapNode* current = root;
    while (current != 0) {
        parent = current;
        if (key == current->key) {
            return;
        } else if (key < current->key) {
            current = current->left;
        } else {
            current = current->right;
        }
    }
    TreapNode* newNode = new TreapNode(key);
    newNode->parent = parent;
    if (parent == 0) {
        root = newNode;
    } else if (key < parent->key) {
        parent->left = newNode;
        heapify(*newNode);
    } else {
        parent->right = newNode;
        heapify(*newNode);
    }
}

void Treap::heapify(TreapNode& node) {
    TreapNode* parent = node.parent;
    while (&node != root && node.priority < parent->priority) {
        if (&node == parent->left) {
            rotate_right(*node.parent);
        }
        if (&node == parent->right) {
            rotate_left(*node.parent);
        }
        parent = node.parent;
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
    TreapNode* node = findNode(key);
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

TreapNode* Treap::findNode(int key) const{
    TreapNode* current = root;
    while (current != 0) {
        if (key == current->key) {
            return current;
        } else if (key < current->key) {
            current = current->left;
        } else {
            current = current->right;
        }
    }
    return 0;
}

bool Treap::containsKey(int key) const {
    return findNode(key) != 0;
}

void Treap::rotate_left(TreapNode& node) {

    TreapNode* n = node.right;
    if (n != 0) {
        n->parent = node.parent;
        if (n->parent != 0) {
            if (n->parent->left == &node) {
                n->parent->left = n;
            } else {
                n->parent->right = n;
            }
        }
        node.right = n->left;
    }
    if (node.right != 0) {
        node.right->parent = &node;
    }
    node.parent = n;
    node.parent->left = &node;
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
            if (&node == n->parent->left) {
                n->parent->left = n;
            } else {
                n->parent->right = n;
            }
        }
        node.left = n->right;
    }
    if (node.left != 0) {
        node.left->parent = &node;
    }
    node.parent = n;
    node.parent->right = &node;
    if (&node == root) {
        root = n;
        root->parent = 0;
    }
}


