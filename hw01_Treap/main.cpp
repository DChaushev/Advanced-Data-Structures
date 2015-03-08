/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#include <iostream>
#include "TreapNode.h"
#include "Treap.h"

using namespace std;

/*
 * mainly (lol, see what I did there) for testing purposes
 */
int main(int argc, char** argv) {

    Treap treap{};

    treap.insert(6);
    treap.insert(2);
    treap.insert(18);
    treap.insert(-1);
    treap.insert(4);
    treap.insert(10);
    treap.insert(3);
    treap.insert(8);
    
    treap.treverse();

    return 0;
}

