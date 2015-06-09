/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on June 9, 2015, 10:52 AM
 */

#include <iostream>
#include <string.h>
#include "BoyerMoore.hpp"

using namespace std;

void boyer_moore_search(BoyerMoore * bm, const char* text, int text_length) {

    int j = 0;
    while (j <= text_length - bm->pattern_length) {

        int i = bm->pattern_length - 1;
        while (i >= 0 && bm->pattern[i] == text[i + j]) --i;
        if (i < 0) {
            cout << j << endl;
            j += bm->good_suffix_rule_table[0];
        } else {
            j += max(bm->good_suffix_rule_table[i], bm->bad_match_table[text[i + j]] - bm->pattern_length + i + 1);
        }
    }
}

/*
 * 
 */
int main(int argc, char** argv) {

    BoyerMoore * bm = new BoyerMoore("example.com");
    bm->test();

    const char * text = "http://www.example.com/something/index.html\nhttps://example.com/somethingelse/\nftp://1.2.3.4/qweqweqweqwe/qweqweqweqwe/q\nftp://aaa:bbbbbb@example.com/1/2/3\nhttps://sub1.sub2.example.org/1/2/file.html\nhttp://blog.example.com/2015/very_important_stuff/";

    boyer_moore_search(bm, text, strlen(text));

    return 0;
}

