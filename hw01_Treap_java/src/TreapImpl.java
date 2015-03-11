
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
        insert(root, key, root);
    }

    private void insert(TreapNode root, int key, TreapNode parent) {
        if (root == null) {
            root = new TreapNode(key);
            if (this.root == null) {
                this.root = root;
            } else {
                if (parent != null) {
                    if (parent.key > root.key) {
                        parent.left = root;
                    } else {
                        parent.right = root;
                    }
                }
            }
            root.parent = parent;
            heapify(root);
        } else if (key < root.key) {
            insert(root.left, key, root);
        } else if (key > root.key) {
            insert(root.right, key, root);
        }
    }

    private void heapify(TreapNode node) {
        TreapNode parent = node.parent;
        if (node != root && node.priority < parent.priority) {
            if (parent.left == node) {
                rotateRight(node.parent);
                heapify(node);
            }
            if (parent.right == node) {
                rotateLeft(node.parent);
                heapify(node);
            }
        }
    }

    @Override
    public void remove(int key) {
        TreapNode node = findNode(root, key);
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

    private TreapNode findNode(TreapNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.key == key) {
            return root;
        }
        if (key < root.key) {
            return findNode(root.left, key);
        }
        if (key > root.key) {
            return findNode(root.right, key);
        }
        return null;
    }

    @Override
    public boolean containsKey(int key) {
        return containsKey(root, key);
    }

    private boolean containsKey(TreapNode root, int key) {
        if (root == null) {
            return false;
        }
        if (root.key == key) {
            return true;
        }
        if (key < root.key) {
            return containsKey(root.left, key);
        }
        if (key > root.key) {
            return containsKey(root.right, key);
        }
        return false;
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
    public String treverse() {
        StringBuilder result = new StringBuilder();
        result.append("===============\n");

        Queue<TreapNode> q = new ConcurrentLinkedQueue<>();

        q.add(root);

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
