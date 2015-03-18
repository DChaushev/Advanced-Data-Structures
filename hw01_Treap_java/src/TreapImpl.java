
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dimitar
 */
public class TreapImpl implements Treap {

    private TreapNode root;

    public TreapImpl() {
        root = null;
    }

    @Override
    public void insert(int key) {
        TreapNode parent = null;
        TreapNode current = root;
        while (current != null) {
            parent = current;
            if (key == current.key) {
                return;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        TreapNode newNode = new TreapNode(key);
        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
            heapify(newNode);
        } else {
            parent.right = newNode;
            heapify(newNode);
        }
    }

    private void heapify(TreapNode node) {
        TreapNode parent = node.parent;
        while (node != root && node.priority < parent.priority) {
            if (parent.left == node) {
                rotateRight(node.parent);
            }
            if (parent.right == node) {
                rotateLeft(node.parent);
            }
            parent = node.parent;
        }
    }

    @Override
    public void remove(int key) {
        TreapNode node = findNode(key);
        if (node != null) {
            while (!(node.left == null && node.right == null)) {
                if (node.left == null) {
                    rotateLeft(node);
                } else if (node.right == null) {
                    rotateRight(node);
                } else if (node.left.priority < node.right.priority) {
                    rotateRight(node);
                } else {
                    rotateLeft(node);
                }
                if (root == node) {
                    root = node.parent;
                }
            }
            if (node.parent.left != null && node == node.parent.left) {
                node.parent.left = null;
            }
            if (node.parent.right != null && node == node.parent.right) {
                node.parent.right = null;
            }
        }
    }

    private TreapNode findNode(int key) {
        TreapNode current = root;
        while (current != null) {
            if (key == current.key) {
                return current;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(int key) {
        return findNode(key) != null;
    }

    private void rotateLeft(TreapNode node) {

        TreapNode n = node.right;
        if (n != null) {
            n.parent = node.parent;
            if (n.parent != null) {
                if (n.parent.left == node) {
                    n.parent.left = n;
                } else {
                    n.parent.right = n;
                }
            }
            node.right = n.left;
        }
        if (node.right != null) {
            node.right.parent = node;
        }
        node.parent = n;
        node.parent.left = node;
        if (node == root) {
            root = n;
            root.parent = null;
        }
    }

    private void rotateRight(TreapNode node) {

        TreapNode n = node.left;
        if (n != null) {
            n.parent = node.parent;
            if (n.parent != null) {
                if (n.parent.left == node) {
                    n.parent.left = n;
                } else {
                    n.parent.right = n;
                }
            }
            node.left = n.right;
        }
        if (node.left != null) {
            node.left.parent = node;
        }
        node.parent = n;
        node.parent.right = node;
        if (node == root) {
            root = n;
            root.parent = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("===============\n");

        Queue<TreapNode> q = new ConcurrentLinkedQueue<>();

        if (root != null) {
            q.add(root);
        }

        while (!q.isEmpty()) {
            TreapNode n = q.peek();

            q.remove();
            result.append(String.format("%d: %.3f ", n.key, n.priority));
            if (n.left != null) {
                result.append(String.format(" left: %d ", n.left.key));
                q.add(n.left);
            }
            if (n.right != null) {
                result.append(String.format(" right: %d ", n.right.key));
                q.add(n.right);
            }

            result.append("\n");
        }

        return result.toString();
    }
}
