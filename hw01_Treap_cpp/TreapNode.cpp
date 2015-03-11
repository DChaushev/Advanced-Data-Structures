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
