# Data-Structures-2
Implementations of some exotic data structures.

These are all the homeworks and exercises that I've done for "Data Structures 2" (Advanced Data Structures) course at the university.<br/>

Some of them are written in Java, and some in C++.

<ul>
<li>
Treap - this is a hybrid between Binary Search Tree and Heap. Every node holds some key and priority value, which is randomly generated number. The treap arranges its elements like BST by their key value and like Heap by the priority. The idea is to keep it somehow balanced. (implemented in both Java and C++)
<br/>http://en.wikipedia.org/wiki/Treap
</li>
<li>
Day-Stout-Warren algorithm - This algorithm is used to make from normal Binary Tree -> complete Binary Tree. (implemented in Java)<br/>
<a href="http://en.wikipedia.org/wiki/Day%E2%80%93Stout%E2%80%93Warren_algorithm">http://en.wikipedia.org/wiki/Day-Stout-Warren_algorithm</a>
</li>
<li>
AVL Tree - This is a balanced binary tree. I am given an interface and some tests and I must implement insert(), delete() and find() operations (implemented in Java) <br/>
<a href="http://en.wikipedia.org/wiki/AVL_tree">http://en.wikipedia.org/wiki/AVL_tree</a><br/>
I found this site to be very usefull: <a href="https://www.cs.usfca.edu/~galles/visualization/AVLtree.html">check it out</a>
</li>
<li>
Splay Tree - This is a binary tree, that keeps as root the last element, operated with (last inserted, last searched for, parent of deleted). The idea is that like in the cache - if you are searching for something - there's a probability you'll search for it again! (implemented in Java) <br/>
http://en.wikipedia.org/wiki/Splay_tree
</li>
<li>
Skew Heap - Heap-ordered binary tree or self-adjusting version of leftist heap. (implemented in Java)<br/>
http://en.wikipedia.org/wiki/Skew_heap
</li>
<li>
HashMap - I think it's needless to say what this is. (implemented in Java and smaller version in C++)</br>
http://en.wikipedia.org/wiki/Hash_table
</li>
<li>
Interval Tree - structure, that has fast updateInterval and getMax in interval operations.<br/>
It is some modified version of the Fenwick tree. (implemented in Java)
</li>
<li>
A persistent data structure is a data structure that always preserves the previous version of itself when it is modified. Such data structures are effectively immutable, as their operations do not (visibly) update the structure in-place, but instead always yield a new updated structure. These types of data structures are particularly common in logical and functional programming. <br/>
http://en.wikipedia.org/wiki/Persistent_data_structure </br>
In this repo you can find Java implementations for:
<ul>
<li>
Persistent Stack
</li>
<li>
Persistent Queue - implemented with two persistent stacks
</li>
</ul>
</li>
<li>
Prefix tree or Trie (implemented in C++) </br>
http://en.wikipedia.org/wiki/Trie
</li>
<li>
Interval tree (implemented in Java) </br>
https://en.wikipedia.org/wiki/Interval_tree </br>
http://www.geeksforgeeks.org/interval-tree/
</li>
<li>
Radix tree (Radix Patricia) (implemented in C++) </br>
https://en.wikipedia.org/wiki/Radix_tree </br>
http://www.mif.vu.lt/~algis/dsax/DsRadix.pdf
</li>
<li>
Suffix array (implemented in Java) </br>
https://en.wikipedia.org/wiki/Suffix_array
</li>
<li>
Boyer Moore string matching algorithm (implemented in C++) </br>
https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm
</li>
<li>
Binomial Heap (implemented in Java) </br>
https://en.wikipedia.org/wiki/Binomial_heap
</li>
</ul>
