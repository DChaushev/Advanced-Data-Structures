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
        for (int i = 0; i < pattern_length; ++i)
            bad_match_table[pattern[i]] = i;
    }

    void preprocess_good_suffixes_rule() {

    }

public:

    BoyerMoore(char* pattern) :
    pattern(pattern) {
        this->pattern_length = strlen(pattern);
        preprocess_bad_character_rule();
        preprocess_good_suffixes_rule();
    }

    void test() {
        cout << "--- bad characters ---" << endl;
        for (unordered_map<int, int>::iterator it = bad_match_table.begin(); it != bad_match_table.end(); it++) {
            cout << char((*it).first) << " " << (*it).first << " " << (*it).second << endl;
        }
        cout << "----------------------" << endl;
    }

    friend void boyer_moore_search(BoyerMoore* bm, char* text, int text_length);

};

void boyer_moore_search(BoyerMoore * bm, char* text, int text_length) {

    int i = bm->pattern_length - 1;
    int j = bm->pattern_length - 1;

    while (i < text_length) {

        if (bm->pattern[j] == text[i]) {
            if (j == 0) {
                cout << i << endl;
                i += bm->pattern_length - min(j, 1 + bm->bad_match_table[text[i]]);
            } else {
                i--;
                j--;
            }
        } else {
            i += bm->pattern_length - min(j, 1 + bm->bad_match_table[text[i]]);
            j = bm->pattern_length - 1;
        }
    }
}

/*
 * 
 */
int main(int argc, char** argv) {

    BoyerMoore * bm = new BoyerMoore("needle");
    bm->test();

    char * text = "helloneedleneedleintneedlehisaysneedletackneedle";
    boyer_moore_search(bm, text, strlen(text));

    return 0;
}

