/* 
 * File:   main.cpp
 * Author: Dimitar
 *
 * Created on June 9, 2015, 10:52 AM
 */

#include <iostream>
#include <string.h>
#include <unordered_map>
#include <algorithm>

using namespace std;

class BoyerMoore {
private:
    char* pattern;
    int pattern_length;

    unordered_map<int, int> bad_match_table;
    int * good_suffix_rule_table;

    void preprocess_bad_character_rule() {
        for (int i = 0; i < pattern_length - 1; ++i)
            bad_match_table[pattern[i]] = pattern_length - i - 1;
    }

    void preprocess_good_suffixes_rule() {
        good_suffix_rule_table = new int[pattern_length];
        int* suff = new int[pattern_length];

        suffixes(suff);

        for (int i = 0; i < pattern_length; i++)
            good_suffix_rule_table[i] = pattern_length;

        for (int i = pattern_length - 1; i >= 0; i--) {
            if (suff[i] == i + 1) {
                for (int j = 0; j < pattern_length - i - 1; j++) {
                    if (good_suffix_rule_table[j] == pattern_length) {
                        good_suffix_rule_table[j] = pattern_length - i - 1;
                    }
                }
            }
        }

        for (int i = 0; i < pattern_length - 2; i++) {
            good_suffix_rule_table[pattern_length - 1 - suff[i]] = pattern_length - i - 1;
        }

        delete[] suff;
    }

    void suffixes(int* suff) {
        suff[pattern_length - 1] = pattern_length;
        int g = pattern_length - 1;
        int f = 0;

        for (int i = pattern_length - 2; i >= 0; --i) {
            if (i > g && suff[i + pattern_length - 1 - f] < i - g) {
                suff[i] = suff[i + pattern_length - 1 - f];
            } else {
                if (i < g) g = i;
                f = i;

                while (g >= 0 && pattern[g] == pattern[g + pattern_length - 1 - f]) g--;
                suff[i] = f - g;
            }
        }
    }
public:

    BoyerMoore(char* pattern) :
    pattern(pattern) {
        this->pattern_length = strlen(pattern);
        preprocess_bad_character_rule();
        preprocess_good_suffixes_rule();
    }

    virtual ~BoyerMoore() {
        delete[] good_suffix_rule_table;
    }

    void test() {
        cout << "--- bad characters ---" << endl;
        for (unordered_map<int, int>::iterator it = bad_match_table.begin(); it != bad_match_table.end(); it++) {
            cout << char((*it).first) << " " << (*it).first << " " << (*it).second << endl;
        }
        cout << "---- good suffixes ---" << endl;
        for (int i = 0; i < pattern_length; i++)
            cout << pattern[i] << " " << good_suffix_rule_table[i] << endl;
        cout << "----------------------" << endl;
    }

    friend void boyer_moore_search(BoyerMoore* bm, const char* text, int text_length);

};

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

