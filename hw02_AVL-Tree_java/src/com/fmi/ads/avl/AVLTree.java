package com.fmi.ads.avl;

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

    @Override
    public void finalize() throws Throwable {
    }

    @Override
    public int getSize() {
        return size;
    }

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
                if (parent.value.compareTo(value) < 0) {
                    parent.rightChild = node.leftChild;
                } else if (parent.value.compareTo(value) > 0) {
                    parent.leftChild = node.leftChild;
                }
                node.leftChild.parent = parent;
            } else if (node.rightChild != null) {
                if (parent.value.compareTo(value) < 0) {
                    parent.rightChild = node.rightChild;
                } else if (parent.value.compareTo(value) > 0) {
                    parent.leftChild = node.rightChild;
                }
                node.rightChild.parent = parent;
            } else {
                if (parent.value.compareTo(value) > 0) {
                    parent.leftChild = null;
                }
                if (parent.value.compareTo(value) < 0) {
                    parent.rightChild = null;
                }
            }
            avl(node);
            size--;
        }
    }

    private Node<T> max(Node<T> node) {
        while (node.rightChild != null) {
            node = node.rightChild;
        }
        return node;
    }

    private void avl(Node<T> node) {

        Node<T> ancestor = node;

        while (ancestor != null) {

            updateHeight(ancestor);

            int balance = getBalance(ancestor);

            // left left
            if (balance > 1 && getBalance(ancestor.leftChild) >= 0) {
                rotateUp(ancestor.leftChild);
                return;
            }
            // right right
            if (balance < -1 && getBalance(ancestor.rightChild) <= 0) {
                rotateUp(ancestor.rightChild);
                return;
            }
            // left right
            if (balance > 1 && getBalance(ancestor.leftChild) < 0) {
                rotateUp(ancestor.leftChild.rightChild);
                rotateUp(ancestor.leftChild);
                return;
            }
            // right left
            if (balance < -1 && getBalance(ancestor.rightChild) > 0) {
                rotateUp(ancestor.rightChild.leftChild);
                rotateUp(ancestor.rightChild);
                return;
            }
            ancestor = ancestor.parent;
        }
    }

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

    private void updateHeight(Node<T> node) {
        int leftHeight = node.leftChild == null ? 0 : node.leftChild.height;
        int righHeight = node.rightChild == null ? 0 : node.rightChild.height;
        node.height = 1 + Math.max(leftHeight, righHeight);
    }

    private int getBalance(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.leftChild) - height(node.rightChild);
    }

    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

}
