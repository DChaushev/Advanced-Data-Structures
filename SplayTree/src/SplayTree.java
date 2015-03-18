
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
public class SplayTree implements SplayTreeInterface {

    private Node root;

    public SplayTree() {
    }

    @Override
    public void insert(int key) {
        Node parent = null;
        Node current = root;
        while (current != null) {
            parent = current;
            if (key == current.key) {
                parent.key = key;
                return;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        Node newNode = new Node(key);
        newNode.parent = parent;
        if (parent == null) {
            this.root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        splay(newNode);
    }

    @Override
    public void delete(int key) {
        Node node = findNode(key);
        if (node == null) {
            return;
        }
        splay(node);
        Node newRoot;
        if (node.left != null) {
            newRoot = max(node.left);
            Node right = root.right;
            root = root.left;

            //Detach old root
            root.parent = null;
            if (right != null) {
                right.parent = null;
            }
            node.left = null;
            node.right = null;

            //Make new root
            splay(newRoot);
            root.right = right;
            if (right != null) {
                right.parent = root;
            }
            root = newRoot;

        } else if (node.right != null) {
            newRoot = min(node.right);
            Node left = root.left;
            root = root.right;

            //Detach old root
            root.parent = null;
            if (left != null) {
                left.parent = null;
            }
            node.left = null;
            node.right = null;

            //Make new root
            splay(newRoot);
            root.left = left;
            if (left != null) {
                left.parent = root;
            }
            root = newRoot;
        } else {
            root = null;
        }

    }

    private Node max(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean contains(int key) {
        Node node = findNode(key);
        if (findNode(key) != null) {
            splay(node);
            return true;
        }
        return false;
    }

    private void splay(Node node) {
        while (node != root) {
            if (node.parent == root) {
                zig(node);
            } else if (node.key < node.parent.key && node.parent.key < node.parent.parent.key) {
                zigZig(node);
            } else if (node.key > node.parent.key && node.parent.key > node.parent.parent.key) {
                zigZig(node);
            } else if (node.key > node.parent.key && node.parent.key < node.parent.parent.key) {
                zigZag(node);
            } else if (node.key < node.parent.key && node.parent.key > node.parent.parent.key) {
                zigZag(node);
            }
        }
    }

    private void zig(Node x) {
        Node y = x.parent;
        x.parent = y.parent;

        if (x.key > y.key) {
            y.right = x.left;
            if (x.left != null) {
                x.left.parent = y;
            }
            x.left = y;
        } else if (x.key < y.key) {
            y.left = x.right;
            if (x.right != null) {
                x.right.parent = y;
            }
            x.right = y;
        }

        if (y.parent != null) {
            if (y.parent.key > y.key) {
                y.parent.left = x;
            } else if (y.parent.key < y.key) {
                y.parent.right = x;
            }
        }
        y.parent = x;
        if (y == this.root) {
            this.root = x;
            x.parent = null;
        }
    }

    private void zigZag(Node x) {
        zig(x);
        zig(x);
    }

    private void zigZig(Node x) {
        if (x.parent != null) {
            zig(x.parent);
        }
        zig(x);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("===============\n");

        Queue<Node> q = new ConcurrentLinkedQueue<>();

        if (root != null) {
            q.add(root);
        }

        while (!q.isEmpty()) {
            Node n = q.peek();

            q.remove();
            result.append(String.format("%d: ", n.key));
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

    private Node findNode(int key) {
        Node current = root;
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

}
