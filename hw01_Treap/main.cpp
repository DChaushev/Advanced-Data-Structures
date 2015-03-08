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

    TreapNode X{1};
    TreapNode Y{2};
    TreapNode C{3};

    X.left = &Y;
    X.left->parent = &X;

    X.right = &C;
    X.right->parent = &X;

    cout << X.key << endl;
    cout << X.left->parent->key << endl;
    cout << X.right->parent->key << endl;
    cout << X.left->key << endl;
    cout << X.right->key << endl;

    
    //=====================
    //test rotations
    //====================
    cout << "Test rotations" << endl;
    cout << "================" << endl;
    
    TreapNode A{4};
    TreapNode B{5};

    Treap treap{};

    treap.root = &X;
    Y.left = &A;
    Y.right = &B;

    cout << "root: " << treap.root->key << endl;
    cout << X.key << ": " << X.left->key << " " << X.right->key << endl;
    cout << Y.key << ": " << Y.left->key << " " << Y.right->key << endl;

    treap.rotate_right(Y);
    cout << endl;

    cout << "root: " << treap.root->key << endl;
    cout << X.key << ": " << X.left->key << " " << X.right->key << endl;
    cout << Y.key << ": " << Y.left->key << " " << Y.right->key << endl;

    treap.rotate_left(X); //Rotate X back to it's original place
    cout << endl;         // output should be the same as before the first rotation

    cout << "root: " << treap.root->key << endl;
    cout << X.key << ": " << X.left->key << " " << X.right->key << endl;
    cout << Y.key << ": " << Y.left->key << " " << Y.right->key << endl;
    
    return 0;
}

