/* 
 * File:   StringHashMap.h
 * Author: Dimitar
 *
 * Created on April 3, 2015, 4:32 PM
 */

#ifndef STRINGHASHMAP_H
#define	STRINGHASHMAP_H

#include <vector>
#include <array>

#include "HashMap.h"

using namespace std;

class StringHashMap : public HashMap {
public:
    StringHashMap();
    StringHashMap(const StringHashMap& orig);
    virtual ~StringHashMap();


    bool containsKey(std::string key);


    void deleteKey(std::string key);


    void put(std::string key, int value);

    int getElement(std::string key);

private:
    static const int MAX_NUMBER_OF_ELEMENTS = 100000;
    static const int BASE = 257;
    static const int MOD = 100007;

    array<vector< pair<string, int > >, MAX_NUMBER_OF_ELEMENTS > hashTable;

    int calculateHash(const string& s);
};

#endif	/* STRINGHASHMAP_H */

