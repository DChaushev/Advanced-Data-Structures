/* 
 * File:   BoyerMoore.hpp
 * Author: Dimitar
 *
 * Created on June 9, 2015, 6:12 PM
 */

#ifndef BOYERMOORE_HPP
#define	BOYERMOORE_HPP
#include <unordered_map>
#include <string>

/**
 * This class makes the Boyer-Moore preprocessing of a pattern.
 * It applies the bad characters rule and the good suffixes one.
 * 
 * This article helped me understand and implement the algorithm:
 * http://www.inf.fh-flensburg.de/lang/algorithmen/pattern/bmen.htm
 * 
 * ...and a couple of lectures on youtube
 * 
 */
class BoyerMoore {
private:
    const char* pattern;
    int pattern_length;

    /*
     * Instead of using an array to keep all of the characters in the ascii table,
     * I'm using this hash table to keep only the ones from the pattern.
     * 
     * That's why I'm using get_bad_match() function - it returns -1 if the character is not
     * in the table.
     */
    std::unordered_map<int, int> bad_match_table;
    int * border;
    int * shift;

    void preprocess_bad_character_rule();

    void preprocess_good_suffixes_rule_case1();

    void preprocess_good_suffixes_rule_case2();

    bool map_contains(int key);

    int get_bad_match(int key);

public:
    BoyerMoore(const char* pattern);

    virtual ~BoyerMoore();

    void test();

    friend int boyer_moore_search(BoyerMoore*, const std::string&, int);

};


#endif	/* BOYERMOORE_HPP */

