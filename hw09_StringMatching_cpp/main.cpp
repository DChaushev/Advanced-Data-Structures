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

void test_boyer_moore() {
    const std::string text = "http://www.example.com/something/index.html\nhttps://example.com/somethingelse/\nftp://1.2.3.4/qweqweqweqwe/qweqweqweqwe/q\nftp://aaa:bbbbbb@example.com/1/2/3\nhttps://sub1.sub2.example.org/1/2/file.html\nhttp://blog.example.com/2015/very_important_stuff/";
    const std::string textLorem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    BoyerMoore * bm1 = new BoyerMoore("example.com");
    boyer_moore_search(bm1, text);
    delete bm1;

    cout << "---------------" << endl;

    BoyerMoore * bm2 = new BoyerMoore("Lorem");
    boyer_moore_search(bm2, textLorem);
    delete bm2;

    cout << "---------------" << endl;

    BoyerMoore * bm3 = new BoyerMoore("Ipsum");
    boyer_moore_search(bm3, textLorem);
    delete bm3;

    cout << "---------------" << endl;
}

void test_file_reading(std::string fileName) {
    vector<std::string> lines = FileReader::getLines(fileName);
    for (auto l : lines) {
        cout << l << endl;
    }
}

/*
 * 
 */
int main(int argc, char** argv) {

    vector<string> domains;
    std::string protocol = "";
    std::string fileName;

    bool protocol_flag = false;
    BoyerMoore * p;

    for (int i = 1; i < argc; i++) {
        cout << "'" << argv[i] << "' ";

        if (strcmp(argv[i], F_FILENAME) == 0)
            fileName = argv[i + 1];
        else if (strcmp(argv[i], F_DOMAIN) == 0)
            domains.push_back(argv[i + 1]);
        else if (strcmp(argv[i], F_PROTOCOL) == 0) {
            protocol = argv[i + 1];
            protocol += ":";
            protocol_flag = true;
        }
    }
    cout << endl << endl;

    vector<string> urls = FileReader::getLines(fileName);
    if (protocol_flag) {
        p = new BoyerMoore(protocol.c_str());
    }

    for (auto domain : domains) {
        int cnt = 0;

        BoyerMoore * bm = new BoyerMoore(domain.c_str());

        for (auto u : urls) {
            if (protocol_flag) {
                if (boyer_moore_search(p, u, protocol.length())) {
                    cnt += boyer_moore_search(bm, u);
                }
            } else {
                cnt += boyer_moore_search(bm, u);
            }
        }
        cout << domain << " - " << cnt << endl;
    }

    return 0;
}

