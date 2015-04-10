/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on April 3, 2015, 4:56 PM
 */

#include <iostream>

#include "HashMap.h"
#include "StringHashMap.h"

using namespace std;

string NAMES[] = {"pesho", "pesh", "gosho", "mitko", "asdasd", "fdwnwe",
    "ivan", "ivanka", "petkan", "aaaaaab", "aaaaaa", "nababatiHvur4iloto123_"};

void testMap(HashMap*& map);

/*
 * 
 */
int main(int argc, char** argv) {

    HashMap* map = new StringHashMap();

    int x = 23;

    for (const auto& name : NAMES) {
        map->put(name, x);
        x += 21;
    }
    
    testMap(map);

    cout << "========== DELETE ==========" << endl;

    map->deleteKey(NAMES[4]);
    map->deleteKey(NAMES[7]);

    testMap(map);

    return 0;
}

void testMap(HashMap*& map) {
    for (const auto& name : NAMES) {
        if ((map)->containsKey(name)) {
            cout << name << ": Yes " << (map)->getElement(name) << endl;
        } else {
            cout << name << ": No" << endl;
        }
    }
}