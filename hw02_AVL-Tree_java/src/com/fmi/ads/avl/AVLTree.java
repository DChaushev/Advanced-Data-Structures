package com.fmi.ads.avl;

import java.util.Stack;

/**
 * Implement your solution here.
 *
 * @author boris.strandjev
 *
 * @param <T> The type of the values to be stored in the tree.
 */
public class AVLTree<T extends Comparable<T>> extends AVLTreeInterface<T> {

    // !!! DO NOT FORGET TO UPDATE THOSE
    public static final String NAME = "Dimitar Todorov Chaushev";
    public static final String MOODLE_NAME = "Димитър Чаушев";
    public static final String FACULTY_NUMBER = "61589";

    // public methods
    public AVLTree() {
    }

    /**
     * I am using Depth first search to traverse through all of the nodes in the
     * tree. For each node set left, right and parent to null.
     *
     * @throws Throwable
     */
    @Override
    public void finalize() throws Throwable {

        try {
            Stack<Node<T>> s = new Stack<>();

            if (root != null) {
                s.add(root);
            }

            while (!s.empty()) {

                Node<T> node = s.pop();

                if (node.leftChild != null) {
                    s.add(node.leftChild);
                }
                if (node.rightChild != null) {
                    s.add(node.rightChild);
                }

                node.leftChild = null;
                node.rightChild = null;
                node.parent = null;
            }
        } finally {
            super.finalize();
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * Binary searches the tree for the given value, starting from the root.
     *
     * @param value
     * @return The node if such value exists. Null otherwise.
     */
    @Override
    public Node<T> findNode(T value) {
        Node<T> current = root;
        int compare;
        while (current != null) {
            compare = value.compareTo(current.value);
            if (compare == 0) {
                return current;
            }
            if (compare < 0) {
                current = current.leftChild;
            }
            if (compare > 0) {
                current = current.rightChild;
            }
        }
        return null;
    }

    /**
     * Binary searches for the place of the new node. If such value exists, it
     * overwrites it. End.
     *
     * If it is a new value, it is inserted at the right place. Then we go up to
     * the root, checking for every node up if the balance is kept.
     *
     * @see #avl(com.fmi.ads.avl.Node)
     * @param value
     */
    @Override
    public void insertNode(T value) {
        Node<T> parent = null;
        Node<T> current = root;
        int cmp;
        while (current != null) {
            parent = current;
            cmp = value.compareTo(current.value);
            if (cmp == 0) {
                parent.value = value;
                return;
            } else if (cmp < 0) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }
        Node<T> newNode = new Node();
        newNode.value = value;
        newNode.height = 1;
        newNode.parent = parent;
        if (parent == null) {
            this.root = newNode;
        } else if (value.compareTo(parent.value) < 0) {
            parent.leftChild = newNode;
        } else {
            parent.rightChild = newNode;
        }
        avl(newNode);
        size++;
    }

    /**
     * Delete by the same way like in a normal binary tree. <br/>
     * 1) If the node has no children - just remove it. <br/>
     * 2) If it has one child - connect the parent with the child. <br/>
     * 3) If it has two children - find the biggest node from it's left subtree.
     * ...Transfer it's value to the node you want to delete and delete it
     * instead. <br/>
     *
     * Then go up to the root to check for disbalance. <br/>
     *
     * @see #avl(com.fmi.ads.avl.Node)
     * @param value
     */
    @Override
    public void deleteNode(T value) {
        Node<T> node = findNode(value);
        if (node == null) {
            return;
        }
        Node<T> parent = node.parent;

        if (node.leftChild != null && node.rightChild != null) {
            Node<T> min = max(node.leftChild);
            deleteNode(min.value);
            node.value = min.value;
        } else {
            if (node.leftChild != null) {
                if(parent == null){
                    root = node.leftChild;
                }
                else if (parent.value.compareTo(value) < 0) {
                    parent.rightChild = node.leftChild;
                } else if (parent.value.compareTo(value) > 0) {
                    parent.leftChild = node.leftChild;
                }
                node.leftChild.parent = parent;
            } else if (node.rightChild != null) {
                if(parent == null){
                    root = node.rightChild;
                }
                else if (parent.value.compareTo(value) < 0) {
                    parent.rightChild = node.rightChild;
                } else if (parent.value.compareTo(value) > 0) {
                    parent.leftChild = node.rightChild;
                }
                node.rightChild.parent = parent;
            } else {
                if(node == root){
                    root = null;
                }
                else if (parent != null && parent.value.compareTo(value) > 0) {
                    parent.leftChild = null;
                }
                else if (parent != null && parent.value.compareTo(value) < 0) {
                    parent.rightChild = null;
                }
            }
            avl(node);
            size--;
        }
    }

    /**
     * Finds the biggest node in a given subtree.
     *
     * @param node
     * @return
     */
    private Node<T> max(Node<T> node) {
        while (node.rightChild != null) {
            node = node.rightChild;
        }
        return node;
    }

    /**
     * I didn't come with a better name for that method. <br/>
     * It takes a node as a parameter. <br/>
     * Then from it goes up to the root and checks for every node if the balance
     * is kept. <br/>
     * If yes - continue up. <br/>
     * If not - perform the corresponding rotations to fix it. <br/>
     *
     * @see #rotateUp(com.fmi.ads.avl.Node)
     * @param node
     */
    private void avl(Node<T> node) {

        Node<T> ancestor = node;

        while (ancestor != null) {

            updateHeight(ancestor);

            int balance = getBalance(ancestor);

            // Left Left Rotation
            if (balance > 1 && getBalance(ancestor.leftChild) >= 0) {
                rotateUp(ancestor.leftChild);
                return;
            }
            // Right Right Rotation
            if (balance < -1 && getBalance(ancestor.rightChild) <= 0) {
                rotateUp(ancestor.rightChild);
                return;
            }
            // Left Right Rotation
            if (balance > 1 && getBalance(ancestor.leftChild) < 0) {
                rotateUp(ancestor.leftChild.rightChild);
                rotateUp(ancestor.leftChild);
                return;
            }
            // Right Left Rotation
            if (balance < -1 && getBalance(ancestor.rightChild) > 0) {
                rotateUp(ancestor.rightChild.leftChild);
                rotateUp(ancestor.rightChild);
                return;
            }
            ancestor = ancestor.parent;
        }
    }

    /**
     * Instead of rotateLeft() and rotateRight() methods I decided to combine
     * them in this one. <br/>
     *
     * I'm using x and y instead of full names because it is more readable for
     * me. At the beginning x is the node we want to rotate, and y is it's
     * parent. After the rotation - x is the parent of y.
     * <PRE>
     *      y                  x
     *    /                      \
     *  x                          y
     *    \          <->         /
     *      *                  *
     *     * *                * *
     *    * * *              * * *
     * rotateUp(x)         rotateUp(y)
     * </PRE>
     *
     * @param x
     */
    private void rotateUp(Node<T> x) {
        Node<T> y = x.parent;
        x.parent = y.parent;

        if (x.value.compareTo(y.value) > 0) {
            y.rightChild = x.leftChild;
            if (x.leftChild != null) {
                x.leftChild.parent = y;
            }
            x.leftChild = y;
        } else if (x.value.compareTo(y.value) < 0) {
            y.leftChild = x.rightChild;
            if (x.rightChild != null) {
                x.rightChild.parent = y;
            }
            x.rightChild = y;
        }

        if (y.parent != null) {
            if (y.parent.value.compareTo(y.value) > 0) {
                y.parent.leftChild = x;
            } else if (y.parent.value.compareTo(y.value) < 0) {
                y.parent.rightChild = x;
            }
        }
        y.parent = x;
        if (y == this.root) {
            this.root = x;
        }
        updateHeight(y);
        updateHeight(x);

    }

    /**
     * Updates the height of a given node.
     *
     * The height is equal to 1 + the max of the heights of it's children.
     *
     * @param node
     */
    private void updateHeight(Node<T> node) {
        int leftHeight = node.leftChild == null ? 0 : node.leftChild.height;
        int righHeight = node.rightChild == null ? 0 : node.rightChild.height;
        node.height = 1 + Math.max(leftHeight, righHeight);
    }

    /**
     * The balance of a given node = height(leftChild) - height(rightChild)
     *
     * @param node
     * @return the calculated balance
     */
    private int getBalance(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.leftChild) - height(node.rightChild);
    }

    /**
     *
     * @param node
     * @return the height of the given node
     */
    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

}
