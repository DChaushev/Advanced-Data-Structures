/* 
 * File:   main.cpp
 * Author: Dimitar Chaushev
 * FN: 61589
 * Algorithm: Boyer-Moore
 *
 * Created on June 9, 2015, 10:52 AM
 */

#include <iostream>
#include <string>
#include <string.h>
#include "BoyerMoore.hpp"
#include "FileReader.hpp"

using namespace std;

const char * F_FILENAME = "-i";
const char * F_DOMAIN = "-d";
const char * F_PROTOCOL = "-p";

/**
 * It's not actually checking only the letters (see ASCII),
 * but it does the job for my purposes.
 * 
 * @param c
 * @return 
 */
bool is_a_letter(char c) {
    int code = int(c);
    return c >= 65 && c <= 122;
}

/**
 * Takes a BoyerMoore object, a text and optionally the length of the text(see the overload below)
 * as a parameters.
 * 
 * Returns the number of occurrences of the pattern in the text.
 * In our case the pattern must match other specific requests. 
 * 
 * This function can take a custom length of the text, because when I'm checking
 * the protocol, I'm passing as length the length of the protocol, thus I'm processing
 * only the beginning.
 * 
 * @param bm
 * @param text
 * @param text_length
 * @return 
 */
int boyer_moore_search(BoyerMoore * bm, const std::string& text, int text_length) {

    int cnt = 0;
    int i = 0;
    int j;

    while (i <= text_length - bm->pattern_length) {
        j = bm->pattern_length - 1;
        while (j >= 0 && bm->pattern[j] == text[i + j]) j--;
        if (j < 0) {

            int suffix_index = i + bm->pattern_length; // the index of the first character after the pattern.
            int preffix_index = i - 1; // the index of the first character before the pattern.
            /*  ____<preffix_index><pattern><suffix_index>____ */

            /*
             * I'm sorry about the expression below...
             * I just didn't want to make another friend function to the BoyerMoore class or another function that takes the text as a parameter.
             * 
             * What is it checking:
             *  - if the character before the start of the pattern is a letter or a single '/'
             *  - if the character after the end of the pattern is something different than a '/' or that's the end.
             * 
             * In both cases - the domain is invalid.
             * 
             */

            if ((text_length > suffix_index && (text[suffix_index] == '.' || is_a_letter(text[suffix_index])))
                    ||
                    ((preffix_index == 0 && text[preffix_index] == '/') ||
                    (preffix_index > 0 && text[preffix_index - 1] != '/' && text[preffix_index] == '/') ||
                    (preffix_index >= 0 && is_a_letter(text[preffix_index])))) {

                int good = bm->shift[j + 1];
                int bad = j - bm->get_bad_match(text[i + j]);
                i += max(good, bad);
            } else {
                cnt++;
                i += bm->shift[0];
            }
        } else {
            int good = bm->shift[j + 1];
            int bad = j - bm->get_bad_match(text[i + j]);
            i += max(good, bad);
        }
    }
    return cnt;
}

int boyer_moore_search(BoyerMoore * bm, const std::string& text) {
    return boyer_moore_search(bm, text, text.length());
}

/*
 * 
 */
int main(int argc, char** argv) {

    vector<std::string> domains;
    std::string file_name;
    std::string protocol;
    bool protocol_flag = false;
    BoyerMoore * protocol_bm;

    for (int i = 1; i < argc; i++) {

        if (strcmp(argv[i], F_FILENAME) == 0)
            file_name = argv[i + 1];
        else if (strcmp(argv[i], F_DOMAIN) == 0)
            domains.push_back(argv[i + 1]);
        else if (strcmp(argv[i], F_PROTOCOL) == 0) {
            protocol = argv[i + 1];
            protocol += ":";
            protocol_flag = true;
        }
    }

    vector<std::string> urls = FileReader::getLines(file_name);
    if (protocol_flag) {
        protocol_bm = new BoyerMoore(protocol.c_str());
    }

    for (auto domain : domains) {
        int cnt = 0;

        BoyerMoore * pattern_bm = new BoyerMoore(domain.c_str());

        for (auto u : urls) {
            if (protocol_flag) {
                if (boyer_moore_search(protocol_bm, u, protocol.length())) {
                    cnt += boyer_moore_search(pattern_bm, u);
                }
            } else {
                cnt += boyer_moore_search(pattern_bm, u);
            }
        }
        cout << domain << " - " << cnt << endl;
        delete pattern_bm;
    }

    if (protocol_flag)
        delete protocol_bm;

    return 0;
}