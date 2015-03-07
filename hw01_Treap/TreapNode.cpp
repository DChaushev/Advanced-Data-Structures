/* 
 * File:   Treap.cpp
 * Author: Dimitar
 * 
 * Created on March 7, 2015, 00:13 AM
 */

#include "TreapNode.h"

#include <cstdlib>

TreapNode::TreapNode(int k) {
    key = k;
    priority = (float) rand() / RAND_MAX;
    left = 0;
    right = 0;
    parent = 0;
}

void TreapNode::set_ket(const int k) {
    key = k;
}

void TreapNode::set_priority(const float p) {
    priority = p;
}

int TreapNode::get_key() {
    return key;
}

float TreapNode::get_priority() {
    return priority;
}


void TreapNode::set_left_child(const TreapNode& left_child) {
    left = &const_cast<TreapNode&>(left_child);
    left->set_parent(*this);
}

void TreapNode::set_right_child(const TreapNode& right_child) {
    right = &const_cast<TreapNode&>(right_child);
    right->set_parent(*this);
}

void TreapNode::set_parent(const TreapNode& parent) {
    this->parent = &const_cast<TreapNode&>(parent);
}

TreapNode* TreapNode::get_left_child() {
    return left;
}

TreapNode* TreapNode::get_righ_child() {
    return right;
}

TreapNode* TreapNode::get_parent() {
    return parent;
}


