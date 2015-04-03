/* 
 * File:   StringHashMap.cpp
 * Author: Dimitar
 * 
 * Created on April 3, 2015, 4:32 PM
 */

#include "StringHashMap.h"

StringHashMap::StringHashMap() {
    fill(hashTable.begin(), hashTable.end(), vector < pair<string, int > >(0));
}

StringHashMap::StringHashMap(const StringHashMap& orig) {
}

StringHashMap::~StringHashMap() {
}

void StringHashMap::put(std::string key, int value) {
    int hash = calculateHash(key);
    //cout << key << ": " << hash << endl;

    if (!containsKey(key))
        hashTable[hash].push_back(make_pair(key, value));
    else {
        for (auto& el : hashTable[hash]) {
            if (el.first == key) {
                el.second = value;
                break;
            }
        }
    }
}

bool StringHashMap::containsKey(std::string key) {
    int hash = calculateHash(key);

    if (hashTable[hash].size() == 0)
        return false;
    else {
        for (const auto& element : hashTable[hash]) {
            if (element.first == key)
                return true;
        }
    }
}

void StringHashMap::deleteKey(std::string key) {
    int hash = calculateHash(key);
    if (hashTable[hash].size() == 0) {
        return;
    } else {
        std::vector< pair<string, int> >::iterator it;
        for (it = hashTable[hash].begin(); it != hashTable[hash].end(); ++it) {
            if (it->first == key) {
                hashTable[hash].erase(it);
                return;
            }
        }
    }
}

int StringHashMap::getElement(std::string key) {
    int hash = calculateHash(key);
    if (hashTable[hash].size() == 0) {
        throw std::invalid_argument("No such element!");
    } else {
        for (const auto& element : hashTable[hash]) {
            if (element.first == key)
                return element.second;
        }
    }
}

int StringHashMap::calculateHash(const string& s) {
    int result = 1;
    for (int i = 0; i < (int) s.length(); i++) {
        result = ((long long) result * BASE + s[i]) % MOD;
    }
    return result;
}

