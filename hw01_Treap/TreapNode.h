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
    
    int get_key();
    float get_priority();
    
    void set_left_child(const TreapNode & left_child);
    void set_right_child(const TreapNode & right_child);
    void set_parent(const TreapNode & parent);
    
    TreapNode* get_left_child();
    TreapNode* get_righ_child();
    TreapNode* get_parent();
    
    
private:
    
    int key;
    float priority;
    TreapNode* parent;
    TreapNode* left;
    TreapNode* right;
};

#endif	/* TREAPNODE_H */

