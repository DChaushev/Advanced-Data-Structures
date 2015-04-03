/* 
 * File:   HashMap.h
 * Author: Dimitar
 *
 * Created on April 3, 2015, 4:28 PM
 */

#ifndef HASHMAP_H
#define	HASHMAP_H

#include <string>

class HashMap {
public:
    virtual void put(std::string key, int value) = 0;
    virtual void deleteKey(std::string key) = 0;
    virtual bool containsKey(std::string key) = 0;
    virtual int getElement(std::string key) = 0;
};

#endif	/* HASHMAP_H */

