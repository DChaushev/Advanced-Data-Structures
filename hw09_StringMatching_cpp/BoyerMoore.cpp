/* 
 * File:   BoyerMoore.cpp
 * Author: Dimitar
 * 
 * Created on June 9, 2015, 6:12 PM
 */

#include "BoyerMoore.hpp"
#include <iostream>
#include <string.h>

BoyerMoore::BoyerMoore(char* pattern) :
pattern(pattern) {
    this->pattern_length = strlen(pattern);

    border = new int[pattern_length + 1];
    shift = new int[pattern_length + 1];

    for (int i = 0; i <= pattern_length; i++) {
        shift[i] = 0;
        border[i] = 0;
    }

    preprocess_bad_character_rule();
    preprocess_good_suffixes_rule_case1();
    preprocess_good_suffixes_rule_case2();
}

BoyerMoore::~BoyerMoore() {
    delete[] border;
    delete[] shift;
}

void BoyerMoore::preprocess_bad_character_rule() {
    for (int i = 0; i < pattern_length; ++i)
        bad_match_table[pattern[i]] = i;
}

void BoyerMoore::preprocess_good_suffixes_rule_case1() {

    int i = pattern_length;
    int j = pattern_length + 1;
    border[i] = j;

    while (i > 0) {
        while (j <= pattern_length && pattern[i - 1] != pattern[j - 1]) {
            if (shift[j] == 0) shift[j] = j - i;
            j = border[j];
        }
        i--;
        j--;
        border[i] = j;
    }
}

void BoyerMoore::preprocess_good_suffixes_rule_case2() {
    int i;
    int j = border[0];

    for (i = 0; i <= pattern_length; i++) {
        if (shift[i] == 0) shift[i] = j;
        if (i == j) j = border[j];
    }
}

bool BoyerMoore::map_contains(int key) {
    std::unordered_map<int, int>::const_iterator got = bad_match_table.find(key);
    return got != bad_match_table.end();
}

int BoyerMoore::get_bad_match(int key) {
    if (map_contains(key))
        return bad_match_table[key];
    else return -1;
}

void BoyerMoore::test() {
    std::cout << "--- bad characters ---" << std::endl;
    for (std::unordered_map<int, int>::iterator it = bad_match_table.begin(); it != bad_match_table.end(); it++) {
        std::cout << char((*it).first) << " " << (*it).first << " " << (*it).second << std::endl;
    }
    std::cout << "---- good suffixes ---" << std::endl;
    for (int i = 0; i <= pattern_length; i++)
        std::cout << pattern[i] << " " << border[i] << std::endl;
    std::cout << "------shift-----------" << std::endl;
    for (int i = 0; i <= pattern_length; i++)
        std::cout << pattern[i] << " " << shift[i] << std::endl;
    std::cout << "----------------------" << std::endl;
}
