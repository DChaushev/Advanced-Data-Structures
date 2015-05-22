/* 
 * File:   prefix_tree.cpp
 * Author: Dimitar
 *
 * Created on May 19, 2015, 1:43 PM
 */

#include <iostream>
#include <fstream>
#include <list>
#include <stack>
#include <vector>
#include <assert.h>
#include <stdlib.h>
#include <string>

using namespace std;

struct Node {
    int data;
    char letter;
    list<Node*> children;

    Node * getChild(char c) {
        for (list<Node*>::iterator it = children.begin(); it != children.end(); it++) {
            if ((*it)->letter == c) {
                return *it;
            }
        }
        return nullptr;
    }
};

class PrefixTree {
private:
    Node * root;

    int* find(char * word) {
        int* result = nullptr;

        int i = 0;
        Node * current = root->getChild(word[i]);
        while (word[i] != '\0') {
            if (current == nullptr || (current != nullptr && current->letter != word[i])) {
                return result;
            }

            if (word[i + 1] == '\0' && current->letter == word[i]) {
                result = &current->data;
            }

            current = current->getChild(word[++i]);
        }
        return result;
    }

public:

    PrefixTree() {
        root = new Node();
    }

    PrefixTree(const PrefixTree& other) {
        this->root = other.root;
    }

    PrefixTree& operator=(const PrefixTree& other) {
        this->root = other.root;
        return *this;
    }

    virtual ~PrefixTree() {
        stack<Node*> s;
        for (list<Node*>::iterator it = root->children.begin(); it != root->children.end(); it++) {
            s.push(*it);
        }

        while (!s.empty()) {
            Node * n = s.top();
            s.pop();

            for (list<Node*>::iterator it = n->children.begin(); it != n->children.end(); it++) {
                s.push(*it);
            }

            delete n;
        }
    }

    void insert(char * word, int value) {

        int i = 0;
        Node * parent = root;
        Node * current = root->getChild(word[i]);

        while (word[i] != '\0') {
            if (current == nullptr) {
                current = new Node();
                current->letter = word[i];
                if (parent != nullptr) {
                    parent->children.push_back(current);
                }
            }

            if (word[i + 1] == '\0' && current->letter == word[i]) {
                current->data = value;
            }

            parent = current;
            current = current->getChild(word[++i]);
        }

    }

    bool contains(char * word) {
        return find(word) != nullptr;
    }

    int get(char * word) {
        int* result = find(word);
        if (result != nullptr) {
            return *result;
        } else cout << "No such element!";
    }

    void display() {
        stack<Node*> s;
        for (list<Node*>::iterator it = root->children.begin(); it != root->children.end(); it++) {
            s.push(*it);
        }

        while (!s.empty()) {
            Node * n = s.top();
            s.pop();

            cout << n->letter << " ";

            for (list<Node*>::iterator it = n->children.begin(); it != n->children.end(); it++) {
                s.push(*it);
            }
        }
    }
};

/*
 * 
 */
int main(int argc, char** argv) {

    PrefixTree * t = new PrefixTree();

    t->insert("baba", 20);
    t->insert("bace", 50);
    t->insert("cat", 5);

    t->display();
    cout << endl;

    cout << "baba: " << t->get("baba") << endl;
    cout << "bace: " << t->get("bace") << endl;
    cout << "cat: " << t->get("cat") << endl;
    cout << t->get("dasdsa") << endl;

    cout << endl;

    vector<string> words;
    string word;

    ifstream file("words.txt");
    while (getline(file, word)) {
        words.push_back(word);
        t->insert(&word[0], rand() % 300);
    }
    file.close();

    for (auto word : words) {
        assert(t->contains(&word[0]));
    }

    // Print the values of the first 42 words:
    for (int i = 0; i < 42; i++) {
        cout << words[i] << endl;
        cout << " " << t->get(&(words[i][0])) << endl;
    }

    delete t;

    return 0;
}

