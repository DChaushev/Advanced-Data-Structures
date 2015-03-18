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

void stressInsert(int n) {
    Treap treap{};
    cout << "Inserting " << n << " elements" << endl;
    std::chrono::time_point<std::chrono::system_clock> start, end;
    start = std::chrono::system_clock::now();
    for (int i = 0; i < n; i++) {
        treap.insert(i);
    }
    end = std::chrono::system_clock::now();

    std::chrono::duration<double> elapsed_seconds = end - start;

    cout << elapsed_seconds.count() << " sec." << endl;
}

/*
 * mainly (lol, see what I did there) for testing purposes
 */
int main(int argc, char** argv) {

    Treap treap{};

//    stressInsert(2000000);
//    stressInsert(4000000);
//    stressInsert(8000000);
//    stressInsert(16000000);
//    stressInsert(32000000);
//    stressInsert(64000000);


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
        treap.remove(8);
        treap.treverse();

        cout << "contains 3: " << treap.containsKey(3) << endl;
        treap.remove(6);
        treap.treverse();
    

    return 0;
}

