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

    int i = 0;
    int j;

    while (i <= text_length - bm->pattern_length) {
        j = bm->pattern_length - 1;
        while (j >= 0 && bm->pattern[j] == text[i + j]) j--;
        if (j < 0) {
            cout << i << endl;
            i += bm->shift[0];
        } else {
            int good = bm->shift[j + 1];
            int bad = j - bm->get_bad_match(text[i + j]);
            i += max(good, bad);
        }
    }
}

void boyer_moore_search(BoyerMoore * bm, const char* text) {
    boyer_moore_search(bm, text, strlen(text));
}

/*
 * 
 */
int main(int argc, char** argv) {

    const char * text = "http://www.example.com/something/index.html\nhttps://example.com/somethingelse/\nftp://1.2.3.4/qweqweqweqwe/qweqweqweqwe/q\nftp://aaa:bbbbbb@example.com/1/2/3\nhttps://sub1.sub2.example.org/1/2/file.html\nhttp://blog.example.com/2015/very_important_stuff/";
    const char * textLorem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    BoyerMoore * bm1 = new BoyerMoore("example.com");
    boyer_moore_search(bm1, text);
    delete bm1;

    cout << "---------------" << endl;

    BoyerMoore * bm2 = new BoyerMoore("Lorem");
    boyer_moore_search(bm2, textLorem);
    delete bm2;

    cout << "---------------" << endl;

    BoyerMoore * bm3 = new BoyerMoore("Ipsum");
    boyer_moore_search(bm3, textLorem, strlen(textLorem));
    delete bm3;

    return 0;
}

