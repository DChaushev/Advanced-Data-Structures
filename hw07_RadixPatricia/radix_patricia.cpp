/* 
 * File:   radix_patricia.cpp
 * Author: Dimitar
 *
 * Created on May 21, 2015, 1:10 PM
 */

#include <iostream>
#include <bitset>
#include <vector>
#include <cstring>
#include <assert.h>

using namespace std;

const int BITS_PER_CHAR = 8;
const int HIGHER_BIT = BITS_PER_CHAR - 1;

int getNthBit(const char c, int n) {
    return (c >> (HIGHER_BIT - n) & 1);
}

int getNthBit(const char * word, int n) {
    char c = word[n / BITS_PER_CHAR];
    return getNthBit(c, n % BITS_PER_CHAR);
}

int findCommonBits(char a, char b) {
    for (int i = HIGHER_BIT; i >= 0; i--) {
        if (((a >> i) & 1) != ((b >> i) & 1)) {
            return HIGHER_BIT - i;
        }
    }
    return BITS_PER_CHAR;
}

int findCommonBits(const char * firstWord, const char * secondWord) {

    int i = 0;
    int commonBits;
    int maxI = min(strlen(firstWord), strlen(secondWord));

    while (i < maxI && (commonBits = findCommonBits(firstWord[i], secondWord[i])) == BITS_PER_CHAR) {
        i++;
    }
    if (i == maxI)
        return maxI * BITS_PER_CHAR;

    commonBits = i * BITS_PER_CHAR + commonBits;
    return commonBits;
}

int getBits(const char * word) {
    return strlen(word) * BITS_PER_CHAR;
}

struct Node {
    Node * children[2]{0, 0};
    int index = -1;
    int bits = 0;
};

class RadixTrie {
private:
    Node * root;
    vector<pair<char*, int> > dictionary;

public:

    RadixTrie() {
        root = 0;
    }

    void insert(char * word, int data) {

        if (root == 0) {
            root = new Node();
            root->index = 0;
            root->bits = getBits(word);
            dictionary.push_back(make_pair(word, data));
            return;
        }

        Node * current = root;
        Node * parent = current;
        int bits = 0;
        int commonBits;
        bool splittingBit;

        while (current != 0) {
            bits += current->bits;
            commonBits = findCommonBits(dictionary[current->index].first, word);

            splittingBit = getNthBit(word, commonBits);

            if (commonBits == getBits(word)) {
                // the word already exists: replace it
                dictionary[current->index].second = data;
                return;
            }

            if (commonBits < current->bits && parent == 0) {
                Node * newRoot = new Node();
                newRoot->bits = commonBits;
                current->bits -= commonBits;
                newRoot->children[splittingBit] = new Node();
                newRoot->children[splittingBit]->bits = getBits(word) - newRoot->bits;
                newRoot->children[splittingBit]->index = dictionary.size();
                newRoot->children[!splittingBit] = current;
                root = newRoot;
                return;
            }

            parent = current;
            current = current->children[splittingBit];
        }

        parent->children[splittingBit] = new Node();
        parent->children[splittingBit]->bits = getBits(word) - commonBits;
        parent->children[splittingBit]->index = dictionary.size();

        dictionary.push_back(make_pair(word, data));

        int oldParentBits = parent->bits;
        parent->bits = getBits(word) - parent->children[splittingBit]->bits;
        if (parent->children[!splittingBit] != 0)
            parent->children[!splittingBit]->bits += (oldParentBits - parent->bits);
        else {
            parent->children[!splittingBit] = new Node();
            parent->children[!splittingBit]->bits += (oldParentBits - parent->bits);
            parent->children[!splittingBit]->index = parent->index;
        }

    }

    int find(char * word) {
        if (root == 0)
            return -1;

        Node * current = root;
        Node * parent = current;
        int bits = 0;
        int commonBits;
        bool splittingBit;

        while (current != 0) {
            bits += current->bits;
            commonBits = findCommonBits(dictionary[current->index].first, word);

            splittingBit = getNthBit(word, commonBits);

            if (commonBits == getBits(word)) {
                // the word already exists: replace it
                return dictionary[current->index].second;
            }

            parent = current;
            current = current->children[splittingBit];
        }

        return -1;
    }

};

/*
 * 
 */
int main(int argc, char** argv) {

    RadixTrie * t = new RadixTrie();
    t->insert("ab", 10);
    t->insert("bad", 6);
    t->insert("cd", 12);

    cout << t->find("ab") << endl;
    cout << t->find("bad") << endl;
    cout << t->find("cd") << endl;
    cout << t->find("abc") << endl;
    

    bool bb = getNthBit('a', 0);
    cout << bb << endl << !bb << endl;

    char a = 'a';
    char b = 'b';
    char one = '1';
    char * abcd = "abcd";
    char D = 'D';

    bitset<8> bitsetDD = bitset<8>(D);
    cout << bitsetDD << endl;

    bitset<8> bitsetA = bitset<8>(a);
    cout << bitsetA; // << endl;
    bitset<8> bitsetB = bitset<8>(b);
    cout << bitsetB << endl;

    for (int i = 0; i < 16; i++)
        if (i < 8)
            assert(getNthBit("ab", i) == bitsetA[8 - i % 8 - 1]);
        else
            assert(getNthBit("ab", i) == bitsetB[8 - i % 8 - 1]);

    assert(findCommonBits('a', 'b') == 6);
    assert(findCommonBits('a', 'D') == 2);

    assert(findCommonBits("ab", "ab") == 16);
    assert(findCommonBits("baba", "ba") == 16);
    assert(findCommonBits("baba", "bace") == 23);
    assert(findCommonBits("ab", "123") == 1);
    assert(findCommonBits("ab", "abc") == 16);
    assert(findCommonBits("ab", "") == 0);

    return 0;
}

