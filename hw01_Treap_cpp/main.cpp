/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:17 PM
 */

#include <iostream>
#include "Treap.h"
#include <cstdlib>
#include <chrono>

using namespace std;

/*
 * mainly (lol, see what I did there) for testing purposes
 */
int main(int argc, char** argv) {

    Treap treap{};

    std::chrono::time_point<std::chrono::system_clock> start, end;
    start = std::chrono::system_clock::now();
    for (int i = 0; i < 1000000; i++) {
        int k = rand() % 10000000 + 1;
        treap.insert(k);
    }
    end = std::chrono::system_clock::now();
    
    std::chrono::duration<double> elapsed_seconds = end-start;
    
    cout << elapsed_seconds.count() << endl;


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
    //    treap.remove(8);
    //    treap.treverse();

    //    cout << treap.containsKey(3) << endl;
    //    treap.remove(6);
    //    treap.treverse();
    //
    //    cout << treap.containsKey(7039) << endl;
    //    treap.remove(7039);
    //    cout << treap.containsKey(29501) << endl;
    //    cout << treap.containsKey(19) << endl;
    //    cout << treap.containsKey(18) << endl;

    return 0;
}

