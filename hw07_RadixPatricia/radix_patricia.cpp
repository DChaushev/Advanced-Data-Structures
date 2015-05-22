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

int findCommonBitsChars(char a, char b) {
    for (int i = HIGHER_BIT; i >= 0; i--) {
        if (((a >> i) & 1) != ((b >> i) & 1)) {
            return HIGHER_BIT - i;
        }
    }
    return BITS_PER_CHAR;
}

int findCommonBitsWords(const char * firstWord, const char * secondWord) {

    int i = 0;
    int commonBits;
    int maxI = min(strlen(firstWord), strlen(secondWord));

    while (i < maxI && (commonBits = findCommonBitsChars(firstWord[i], secondWord[i])) == BITS_PER_CHAR) {
        i++;
    }
    if (i == maxI)
        return maxI * BITS_PER_CHAR;

    commonBits = i * BITS_PER_CHAR + commonBits;
    return commonBits;
}

struct Node {
    Node* left = 0;
    Node* right = 0;
    int index = -1;
    int bits = 0;
};

class RadixTrie {
private:
    Node * root;
    vector<pair<char*, int> > dictionary;

    int getData(int index) const {
        return dictionary[index].second;
    }

    char* getWord(int index, int bits) const {

        int begin = 0;
        int end = bits / BITS_PER_CHAR;
        //        if (end == 0) {
        //            return "";
        //        }

        char* word = dictionary[index].first;

        char* temp = new char[end - begin + 1];
        strncpy(temp, word + begin, end - begin + 1);
        temp[end - begin + 1] = '\0';
        return temp;
    }

    void changeData(int index, int data) {
        dictionary[index].second = data;
    }

    bool isWord(int index, int bits) const {
        if (index == -1) return false;
        return strlen(dictionary[index].first) * BITS_PER_CHAR == bits;
    }

    bool isLeaf(Node * n) const {
        return n->left == 0 && n->right == 0;
    }

public:

    RadixTrie() {
        root = new Node();
    }

    void insert(char * word, int data) {

        //TODO - DO MAGIC

        Node * current = root;
        Node * parent = current;

        if (getNthBit(word[0], 0) == 0)
            current = current->left;
        else
            current = current->right;
        int bits = 0;

        while (current != 0) {
            bits += current->bits;

            char * currentWord = getWord(current->index, bits);
            int commonBits = findCommonBitsWords(currentWord, word);

            if (commonBits < strlen(currentWord) * BITS_PER_CHAR) {
                Node * middleNode = new Node();
                middleNode->index = current->index;
                middleNode->bits = parent->bits + commonBits; //commonBits; 
                current->bits -= middleNode->bits;

                Node * newNode = new Node();
                newNode->index = dictionary.size();
                dictionary.push_back(make_pair(word, data));
                newNode->bits = strlen(word) * BITS_PER_CHAR - middleNode->bits;

                if (current == parent->left)
                    parent->left = middleNode;
                else
                    parent->right = middleNode;

                if (getNthBit(currentWord[commonBits % BITS_PER_CHAR], commonBits) == 0) {
                    middleNode->left = current;
                    middleNode->right = newNode;
                } else {
                    middleNode->left = newNode;
                    middleNode->right = current;
                }
                return;
            }

            if (commonBits == strlen(word) * BITS_PER_CHAR && strlen(word) == strlen(currentWord)) {
                //then word already exists - just replace the data.
                changeData(current->index, data);
                return;
            }

            int differentBit = getNthBit(word, commonBits);
            parent = current;
            if (differentBit == 0)
                current = current->left;
            else
                current = current->right;

        }

        if (current == 0) {
            Node * newNode = new Node();

            if (current == parent->left) {
                parent->left = newNode;
            } else {
                parent->right = newNode;
            }

            newNode->index = dictionary.size();
            dictionary.push_back(make_pair(word, data));
            newNode->bits = strlen(word) * (HIGHER_BIT + 1) - parent->bits;
            return;
        }







        //        Node * current = root;
        //        Node * parent = current;
        //        int bits = current->bits;
        //        if (getNthBit(word[0], 0) == 0)
        //            current = current->left;
        //        else
        //            current = current->right;
        //
        //        //TODO - navigation to the node
        //        //        int bits = current->bits;
        //        //
        //        //        while (current != 0 && ) {
        //        //        }
        //
        //        if (current == 0) {
        //            dictionary.push_back(make_pair(word, data));
        //            Node * newNode = new Node();
        //
        //            if (current == parent->left) {
        //                parent->left = newNode;
        //            } else {
        //                parent->right = newNode;
        //            }
        //
        //            newNode->index = dictionary.size() - 1;
        //            newNode->bits = strlen(word) * (HIGHER_BIT + 1) - parent->bits;
        //            return;
        //        } else {
        //            bits += current->bits;
        //            char* currentWord = getWord(current->index, bits);
        //            int commonBits = findCommonBitsWords(word, currentWord);
        //
        //            if (commonBits == max(strlen(word) * BITS_PER_CHAR, strlen(currentWord) * BITS_PER_CHAR)) {
        //                //then word already exists - just replace the data.
        //                changeData(current->index, data);
        //            } else {
        //                //split with new node
        //                Node * middleNode = new Node();
        //                middleNode->index = current->index;
        //                middleNode->bits = commonBits;
        //                current->bits -= commonBits;
        //
        //                Node * newNode = new Node();
        //                newNode->index = dictionary.size();
        //                dictionary.push_back(make_pair(word, data));
        //                newNode->bits = strlen(word) * BITS_PER_CHAR - middleNode->bits;
        //
        //                if (current == parent->left)
        //                    parent->left = middleNode;
        //                else
        //                    parent->right = middleNode;
        //
        //                if (getNthBit(currentWord[commonBits % BITS_PER_CHAR], commonBits) == 0) {
        //                    middleNode->left = current;
        //                    middleNode->right = newNode;
        //                } else {
        //                    middleNode->left = newNode;
        //                    middleNode->right = current;
        //                }
        //            }
        //        }
    }

    int find(const char* word) const {

        //        int bits = 0;
        //
        //        cout << getWord(root->left->index, bits += root->left->bits) << endl;
        //        cout << getWord(root->left->left->index, bits + root->left->left->bits) << endl;
        //        cout << getWord(root->left->right->index, bits + root->left->right->bits) << endl;
        //
        //        bits = root->bits;
        //
        //        cout << isWord(root->index, bits += root->bits) << endl;
        //        cout << isWord(root->left->index, bits += root->left->bits) << endl;
        //        cout << isWord(root->left->left->index, bits + root->left->left->bits) << endl;
        //        cout << isWord(root->left->right->index, bits + root->left->right->bits) << endl;

        Node * current = root;
        if (getNthBit(word[0], 0) == 0)
            current = current->left;
        else
            current = current->right;
        int bits = 0;

        while (current != 0) {
            bits += current->bits;

            if (isWord(current->index, bits)) {
                char* currWord = getWord(current->index, bits);
                if (strcmp(currWord, word) == 0)
                    return dictionary[current->index].second;
            }

            int commonBits = findCommonBitsWords(getWord(current->index, bits), word);
            if (commonBits == 0)
                return -1;

            int differentBit = getNthBit(word, commonBits);

            if (differentBit == 0)
                current = current->left;
            else
                current = current->right;

        }
        return -1;
    }

};

/*
 * 
 */
int main(int argc, char** argv) {

    RadixTrie* trie = new RadixTrie();
    trie->insert("ab", 6);
    //    trie->insert("ab", 66);
    trie->insert("bad", 10);
    trie->insert("baba", 120);

    //cout << trie->find("dsa") << endl;
    cout << trie->find("ab") << endl;
    cout << trie->find("bad") << endl;
    cout << trie->find("baba") << endl;


    char a = 'a';
    char b = 'b';
    char one = '1';


    bitset<8> bit = bitset<8>(a);
    cout << bit; // << endl;
    bit = bitset<8>(b);
    cout << bit << endl;

    for (int i = 0; i < 16; i++)
        cout << getNthBit("ab", i);
    cout << endl;

    bit = bitset<8>('1');
    cout << bit << endl;
    //    for (int i = 0; i < 8; i++)
    //        cout << getNthBit(b, i);
    //    cout << endl;

    //    cout << findCommonBitsChars(a, b) << endl;
    //    cout << findCommonBitsWords("ab", "ab") << endl;
    //    cout << findCommonBitsWords("baba", "ba") << endl;
    //    cout << findCommonBitsWords("baba", "bace") << endl;
    //    cout << findCommonBitsWords("ab", "123") << endl;

    assert(findCommonBitsWords("ab", "ab") == 16);
    assert(findCommonBitsWords("baba", "ba") == 16);
    assert(findCommonBitsWords("baba", "bace") == 23);
    assert(findCommonBitsWords("ab", "123") == 1);
    assert(findCommonBitsWords("ab", "") == 0);

    cout << trie->find("ab") << endl;
    cout << trie->find("bad") << endl;

    return 0;
}

