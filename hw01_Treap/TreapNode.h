/* 
 * File:   TreapNode.h
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#ifndef TREAPNODE_H
#define	TREAPNODE_H

#include <cstdlib>

class TreapNode {
public:

    TreapNode(int k) {
        key = k;
        priority = (float) rand() / RAND_MAX;
        left = 0;
        right = 0;
    }

    int key;
    float priority;
    TreapNode* left;
    TreapNode* right;
};

#endif	/* TREAPNODE_H */

