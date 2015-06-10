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

class BoyerMoore {
private:
    const char* pattern;
    int pattern_length;

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

    friend void boyer_moore_search(BoyerMoore*, const std::string&);

};


#endif	/* BOYERMOORE_HPP */

