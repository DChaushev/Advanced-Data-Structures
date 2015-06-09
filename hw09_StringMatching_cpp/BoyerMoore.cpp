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
    preprocess_bad_character_rule();
    preprocess_good_suffixes_rule();
}

BoyerMoore::~BoyerMoore() {
    delete[] good_suffix_rule_table;
}

void BoyerMoore::preprocess_bad_character_rule() {
    for (int i = 0; i < pattern_length - 1; ++i)
        bad_match_table[pattern[i]] = pattern_length - i - 1;
}

void BoyerMoore::preprocess_good_suffixes_rule() {
    good_suffix_rule_table = new int[pattern_length];
    int* suffixes = new int[pattern_length];

    calc_suffixes(suffixes);

    for (int i = 0; i < pattern_length; i++)
        good_suffix_rule_table[i] = pattern_length;

    for (int i = pattern_length - 1; i >= 0; i--) {
        if (suffixes[i] == i + 1) {
            for (int j = 0; j < pattern_length - i - 1; j++) {
                if (good_suffix_rule_table[j] == pattern_length) {
                    good_suffix_rule_table[j] = pattern_length - i - 1;
                }
            }
        }
    }

    for (int i = 0; i < pattern_length - 2; i++) {
        good_suffix_rule_table[pattern_length - 1 - suffixes[i]] = pattern_length - i - 1;
    }

    delete[] suffixes;
}

void BoyerMoore::calc_suffixes(int* suffixes) {
    suffixes[pattern_length - 1] = pattern_length;
    int g = pattern_length - 1;
    int f = 0;

    for (int i = pattern_length - 2; i >= 0; --i) {
        if (i > g && suffixes[i + pattern_length - 1 - f] < i - g) {
            suffixes[i] = suffixes[i + pattern_length - 1 - f];
        } else {
            if (i < g) g = i;
            f = i;

            while (g >= 0 && pattern[g] == pattern[g + pattern_length - 1 - f]) g--;
            suffixes[i] = f - g;
        }
    }
}

void BoyerMoore::test() {
    std::cout << "--- bad characters ---" << std::endl;
    for (std::unordered_map<int, int>::iterator it = bad_match_table.begin(); it != bad_match_table.end(); it++) {
        std::cout << char((*it).first) << " " << (*it).first << " " << (*it).second << std::endl;
    }
    std::cout << "---- good suffixes ---" << std::endl;
    for (int i = 0; i < pattern_length; i++)
        std::cout << pattern[i] << " " << good_suffix_rule_table[i] << std::endl;
    std::cout << "----------------------" << std::endl;
}

