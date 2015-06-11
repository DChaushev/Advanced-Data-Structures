/* 
 * File:   main.cpp
 * Author: Dimitar
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

int boyer_moore_search(BoyerMoore * bm, const std::string& text, int text_length) {

    int cnt = 0;
    int i = 0;
    int j;

    while (i <= text_length - bm->pattern_length) {
        j = bm->pattern_length - 1;
        while (j >= 0 && bm->pattern[j] == text[i + j]) j--;
        if (j < 0) {
            if ((text_length > i + bm->pattern_length && text[i + bm->pattern_length] == '.') || (i - 1 == 0 && text[i - 1] == '/') || (i - 1 > 0 && text[i - 2] != '/' && text[i - 1] == '/')) {
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
    }

    return 0;
}