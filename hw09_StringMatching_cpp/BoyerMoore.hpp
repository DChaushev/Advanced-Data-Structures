/* 
 * File:   BoyerMoore.hpp
 * Author: Dimitar
 *
 * Created on June 9, 2015, 6:12 PM
 */

#ifndef BOYERMOORE_HPP
#define	BOYERMOORE_HPP
#include <unordered_map>

class BoyerMoore {
private:
    char* pattern;
    int pattern_length;

    std::unordered_map<int, int> bad_match_table;
    int * good_suffix_rule_table;

    void preprocess_bad_character_rule();

    void preprocess_good_suffixes_rule();

    void calc_suffixes(int* suffixes);

public:

    BoyerMoore(char* pattern);

    virtual ~BoyerMoore();

    void test();

    friend void boyer_moore_search(BoyerMoore* bm, const char* text, int text_length);

};


#endif	/* BOYERMOORE_HPP */

