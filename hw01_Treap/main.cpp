/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#include <iostream>
#include "TreapNode.h"

using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {

    TreapNode node{1};
    TreapNode left{2};
    TreapNode right{3};
    
    node.set_left_child(left);
    node.set_right_child(right);
    
    cout << node.get_key() << endl;
    cout << node.get_left_child()->get_parent()->get_key() << endl;
    cout << node.get_righ_child()->get_parent()->get_key() << endl;
    cout << node.get_left_child()->get_key() << endl;
    cout << node.get_righ_child()->get_key() << endl;
    cout << node.get_priority() << endl;
    cout << node.get_left_child()->get_priority() << endl;
    cout << node.get_righ_child()->get_priority() << endl;
    
    
    
    return 0;
}

