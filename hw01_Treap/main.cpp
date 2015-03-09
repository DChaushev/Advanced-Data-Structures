/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#include <iostream>
#include "Treap.h"
#include <cstdlib>

using namespace std;

/*
 * mainly (lol, see what I did there) for testing purposes
 */
int main(int argc, char** argv) {

    Treap treap{};

        for (int i = 0; i < 10000; i++) {
            int k = rand() % 100000 + 1;
            treap.insert(k);
        }

//    treap.insert(6);
//    treap.treverse();
//    treap.insert(2);
//    treap.treverse();
//    treap.insert(18);
//    treap.treverse();
//    treap.insert(-1);
//    treap.treverse();
//    treap.insert(4);
//    treap.treverse();
//    treap.insert(10);
//    treap.treverse();
//    treap.insert(3);
//    treap.treverse();
//    treap.insert(8);
//    treap.treverse();


    cout << treap.containsKey(3) << endl;
    treap.remove(6);
    treap.treverse();

    cout << treap.containsKey(7039) << endl;
    treap.remove(7039);
    cout << treap.containsKey(29501) << endl;
    cout << treap.containsKey(19) << endl;
    cout << treap.containsKey(18) << endl;

    return 0;
}

