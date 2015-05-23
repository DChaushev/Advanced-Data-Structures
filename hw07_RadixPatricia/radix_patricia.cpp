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

    static int nodesCounter;

    RadixTrie() {
        root = 0;
    }

    void insert(const char * word, int data) {

        if (root == 0) {
            root = new Node();

            nodesCounter++;

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

                nodesCounter++;

                newRoot->bits = commonBits;
                current->bits -= commonBits;
                newRoot->children[splittingBit] = new Node();

                nodesCounter++;

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

        nodesCounter++;

        parent->children[splittingBit]->bits = getBits(word) - commonBits;
        parent->children[splittingBit]->index = dictionary.size();

        dictionary.push_back({word, data});

        int oldParentBits = parent->bits;
        parent->bits = getBits(word) - parent->children[splittingBit]->bits;
        if (parent->children[!splittingBit] != 0)
            parent->children[!splittingBit]->bits += (oldParentBits - parent->bits);
        else {
            parent->children[!splittingBit] = new Node();

            nodesCounter++;

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

void test();
void task(char *, char *);
void testBitOperations();
void testInsertAndFind();
void testFromFile();

int RadixTrie::nodesCounter = 0;

/*
 * 
 */
int main(int argc, char** argv) {

    test();
    task(argv[1], argv[2]);

    return 0;
}

void test() {
    testBitOperations();
    testInsertAndFind();

    cout << "All tests passed!" << endl;
}

struct Output {
    const char * word;
    int data;
};

bool operator>(const Output &o1, const Output &o2) {
    return o1.data > o2.data;
}

void task(char * dict, char * text) {

    RadixTrie * trie = new RadixTrie();
    vector<char *> words;

    ifstream input(dict);
    while (!input.eof()) {
        string line;
        int data;
        input >> line;
        input >> data;
        char * word = new char [line.length() + 1];
        words.push_back(word);
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
        //        cout << word << " " << result << endl;
    }

    sort(output.begin(), output.end(), greater<Output>());

    for (int i = 0; i < output.size(); i++)
        cout << output[i].word << " " << output[i].data << endl;
}

void testInsertAndFind() {
    RadixTrie * t = new RadixTrie();
    t->insert("ab", 1);
    t->insert("bad", 2);
    t->insert("cd", 3);
    t->insert("baba", 4);
    t->insert("bace", 5);
    t->insert("babab", 6);

    //    cout << t->find("ab") << endl;
    //    cout << t->find("bad") << endl;
    //    cout << t->find("cd") << endl;
    //    cout << t->find("abc") << endl;
    //    cout << t->find("baba") << endl;
    //    cout << t->find("bace") << endl;
    //    cout << t->find("babab") << endl;

    assert(t->find("ab") == 1);
    assert(t->find("bad") == 2);
    assert(t->find("cd") == 3);
    assert(t->find("abc") == -1);
    assert(t->find("baba") == 4);
    assert(t->find("bace") == 5);
    assert(t->find("babab") == 6);

    t->insert("cd", 33);
    //    cout << t->find("cd") << endl;
    assert(t->find("cd") == 33);

    vector<int> withPrefixBA = t->getAllWithPrefix("ba");
    for (int i = 0; i < withPrefixBA.size(); i++) {
        cout << withPrefixBA[i] << endl;
    }
}

void testBitOperations() {
    bool bb = getNthBit('a', 0);
    assert(bb == 0);
    assert(!bb == 1);

    char a = 'a';
    char b = 'b';

    bitset<8> bitsetA = bitset<8>(a);
    bitset<8> bitsetB = bitset<8>(b);

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
}

void testFromFile() {
    std::chrono::time_point<std::chrono::system_clock> start, end;
    std::chrono::duration<double> elapsed_seconds;

    double time = 0;
    RadixTrie * trie = new RadixTrie();

    int n = 1689295;
    int i = 0;
    ifstream input("words2.txt");
    cout << "inserting..." << endl;
    while (!input.eof()) {
        string line;
        input >> line;
        char * word = new char [line.length() + 1];
        if (i == 1689298)
            cout << "-----" << word << endl;
        strcpy(word, line.c_str());
        start = std::chrono::system_clock::now();
        trie->insert(word, i++);
        end = std::chrono::system_clock::now();
        elapsed_seconds = end - start;
        time += elapsed_seconds.count();
        //                if (i == n) break;
    }
    cout << "Nodes: " << RadixTrie::nodesCounter << endl;
    cout << "Inserting: " << time << " secs" << endl;

    i = 0;
    ifstream input2("words2.txt");
    cout << "testing..." << endl;
    while (!input2.eof()) {
        string line;
        input2 >> line;
        char * word = new char [line.length() + 1];
        strcpy(word, line.c_str());
        start = std::chrono::system_clock::now();
        int ans = trie->find(word);
        end = std::chrono::system_clock::now();
        elapsed_seconds = end - start;
        time += elapsed_seconds.count();
        if (ans != i) {
            cout << word << " : " << i << " != " << trie->find(word) << endl;
            break;
        }
        i++;
        //        assert(trie->find(word) == i++);
        //                if (i == n) break;
    }

    cout << "total: " << time << " sec" << endl;
}