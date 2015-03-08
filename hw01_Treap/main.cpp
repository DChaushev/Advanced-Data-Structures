/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#include <iostream>
#include "Treap.h"

using namespace std;

/*
 * mainly (lol, see what I did there) for testing purposes
 */
int main(int argc, char** argv) {

    Treap treap{};

    treap.insert(6);
    treap.treverse();
    treap.insert(2);
    treap.treverse();
    treap.insert(18);
    treap.treverse();
    treap.insert(-1);
    treap.treverse();
    treap.insert(4);
    treap.treverse();
    treap.insert(10);
    treap.treverse();
    treap.insert(3);
    treap.treverse();
    treap.insert(8);
    treap.treverse();

    return 0;
}

