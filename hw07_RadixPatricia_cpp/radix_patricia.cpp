/* 
 * File:   radix_patricia.cpp
 * Author: Dimitar
 *
 * Created on May 21, 2015, 1:10 PM
 * 
 * This is the hardest task I've ever done and the ugliest code I've written in a long time...
 * Also I wasn't able to finish it...
 * I lack the nerves and the time.
 * 
 * Also, You need to compile it with c++11.
 * 
 */

#include <iostream>
#include <bitset>
#include <vector>
#include <cstring>
#include <assert.h>
#include <fstream>
#include <cstdlib>
#include <chrono>
#include <stack>
#include <set>
#include <algorithm>

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
    int len = strlen(word);
    return len * BITS_PER_CHAR;
}

struct Node {
    Node * children[2]{0, 0};
    int index = -1;
    int bits = 0;
};

struct Map {
    const char * word;
    int data;
};

class RadixTrie {
private:
    Node * root;
    vector<Map> dictionary;

    bool isWord(int index, int bits) {
        return getBits(dictionary[index].word) == bits;
    }

    int getData(int index) {
        return dictionary[index].data;
    }

    pair<Node*, int> getStartNode(const char * prefix) {

        pair<Node*, int> result;
        result.first = 0;
        result.second = 0;

        Node * current = root;
        Node * parent = current;
        int bits = 0;
        int commonBits;
        bool splittingBit;

        while (current != 0) {
            bits += current->bits;
            commonBits = findCommonBits(dictionary[current->index].word, prefix);

            splittingBit = getNthBit(prefix, commonBits);

            if (commonBits == getBits(prefix)) {
                result.first = current;
                result.second = bits;
                return result;
            }

            parent = current;
            current = current->children[splittingBit];
        }
        return result;
    }

public:

    RadixTrie() {
        root = 0;
    }

    void insert(const char * word, int data) {

        if (root == 0) {
            root = new Node();
            root->index = 0;
            root->bits = getBits(word);
            dictionary.push_back({word, data});
            return;
        }

        Node * current = root;
        Node * parent = current;
        int bits = 0;
        int commonBits;
        bool splittingBit;

        while (current != 0) {
            bits += current->bits;
            const char * commonWord = dictionary[current->index].word;
            commonBits = findCommonBits(commonWord, word);

            splittingBit = getNthBit(word, commonBits);

            if (commonBits == getBits(word)) {
                // the word already exists: replace it
                dictionary[current->index].data = data;
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

        dictionary.push_back({word, data});

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

    int find(const char * word) {
        if (root == 0)
            return -1;

        Node * current = root;
        Node * parent = current;
        int bits = 0;
        int commonBits;
        bool splittingBit;

        while (current != 0) {
            bits += current->bits;
            commonBits = findCommonBits(dictionary[current->index].word, word);

            splittingBit = getNthBit(word, commonBits);

            if (commonBits == getBits(word)) {
                // that's the word
                return dictionary[current->index].data;
            }

            parent = current;
            current = current->children[splittingBit];
        }

        return -1;
    }

    vector<int> getAllWithPrefix(const char* prefix) {
        set<int> resultSet;
        vector<int> result;

        pair<Node*, int> start = getStartNode(prefix);

        stack<pair<Node*, int> > s;
        if (start.first != 0)
            s.push(make_pair(start.first, start.second));

        while (!s.empty()) {

            pair<Node*, int> n = s.top();
            s.pop();

            if (findCommonBits(prefix, dictionary[n.first->index].word) == getBits(prefix)) {
                resultSet.insert(getData(n.first->index));
            }

            for (int i = 0; i <= 1; i++) {
                if (n.first->children[i] != 0)
                    s.push(make_pair(n.first->children[i], n.second + n.first->children[i]->bits));
            }

        }
        for (set<int>::iterator it = resultSet.begin(); it != resultSet.end(); it++)
            result.push_back(*it);
        return result;
    }

};

void task(char *, char *);

/*
 * 
 */
int main(int argc, char** argv) {

    task(argv[1], argv[2]);

    return 0;
}

struct Output {
    const char * word;
    int data;
};

bool operator<(const Output &o1, const Output &o2) {
    return o1.data > o2.data;
}

void task(char * dict, char * text) {

    RadixTrie * trie = new RadixTrie();

    ifstream input(dict);
    while (!input.eof()) {
        string line;
        int data;
        input >> line;
        input >> data;
        char * word = new char [line.length() + 1];
        strcpy(word, line.c_str());
        trie->insert(word, data);
    }

    vector<Output> output;

    ifstream input2(text);
    while (!input2.eof()) {
        string line;
        input2 >> line;
        char * word = new char [line.length() + 1];
        strcpy(word, line.c_str());

        vector<int> v = trie->getAllWithPrefix(word);
        int result = 0;
        for (int i = 0; i < v.size(); i++) {
            result += v[i];
        }

        output.push_back({word, result});
    }

    sort(output.begin(), output.end());

    for (int i = 0; i < output.size(); i++)
        cout << output[i].word << " " << output[i].data << endl;
}
