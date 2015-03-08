/* 
 * File:   TreapNode.h
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#ifndef TREAPNODE_H
#define	TREAPNODE_H

class TreapNode {
public:

    TreapNode(int k);

    void set_key(const int k);
    void set_priority(const float p);

    int key;
    float priority;
    TreapNode* parent;
    TreapNode* left;
    TreapNode* right;
};

#endif	/* TREAPNODE_H */

