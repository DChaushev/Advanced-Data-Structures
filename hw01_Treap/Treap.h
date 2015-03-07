/* 
 * File:   Treap.h
 * Author: Dimitar
 *
 * Created on March 7, 2015, 10:32 PM
 */

#ifndef TREAP_H
#define	TREAP_H

class Treap
{
	
public:
  Treap();
  ~Treap();
  
  /**
  * Inserts a node in the treap with the provided @key
  * 
  * @param key: key to be inserted
  */
  void insert(int key);
  
  /**
  * Removes the node from the treap with the provided @key
  * 
  * @param key: key to be removed
  */
  void remove(int key);
  
  /**
  * Checks whether a given @key is already in the treap
  * 
  * @param key: key to be searched for
  * @return true, if the key is in the treap, and false, otherwise
  */
  bool containsKey(int key) const;
  
private:
  Treap& operator=(const Treap&); // Do not implement me!
  Treap(const Treap&); // Do not implement me!
};

#endif	/* TREAP_H */

