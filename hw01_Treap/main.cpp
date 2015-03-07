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
    
    node.left = &left;
    node.right = &right;
    
    cout << node.key << endl;
    cout << node.left->key << endl;
    cout << node.right->key << endl;
    cout << node.priority << endl;
    cout << node.left->priority << endl;
    cout << node.right->priority << endl;
    
    
    
    return 0;
}

