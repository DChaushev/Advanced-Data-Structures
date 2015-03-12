
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author grade
 * @param <K>
 * @param <V>
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    private TreeNode root;
    private int numberOfElements;

    public BinarySearchTree() {
        root = null;
        numberOfElements = 0;
    }

    public void insert(K key, V value) {

        TreeNode parent = null;
        TreeNode current = root;
        int cmp;
        while (current != null) {
            parent = current;
            cmp = key.compareTo(current.key);
            if (cmp == 0) {
                parent.value = value;
                return;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (parent == null) {
            root = new TreeNode(key, value);
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = new TreeNode(key, value);
        } else {
            parent.right = new TreeNode(key, value);
        }
        numberOfElements++;
        if (numberOfElements % 50 == 0) {
            dsw();
        }
    }

    public V get(K key) {
        TreeNode node = findNode(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public void remove(K key) {

        TreeNode node = findNode(key);
        if (node == null) {
            return;
        }
        TreeNode parent = parent(key);

        if (node.left != null && node.right != null) {
            TreeNode min = min(node.right);
            remove(min.key);
            node.key = min.key;
            node.value = min.value;
        } else {
            if (node.left != null) {
                if (parent.key.compareTo(key) < 0) {
                    parent.right = node.left;
                } else if (parent.key.compareTo(key) > 0) {
                    parent.left = node.left;
                }
            } else if (node.right != null) {
                if (parent.key.compareTo(key) < 0) {
                    parent.right = node.right;
                } else if (parent.key.compareTo(key) > 0) {
                    parent.left = node.right;
                }
            } else {
                if (parent.key.compareTo(key) > 0) {
                    parent.left = null;
                }
                if (parent.key.compareTo(key) < 0) {
                    parent.right = null;
                }
            }
            numberOfElements--;
        }
    }

    private TreeNode min(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private TreeNode max(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private TreeNode findNode(K key) {
        TreeNode current = root;
        int compare;
        while (current != null) {
            compare = key.compareTo(current.key);
            if (compare == 0) {
                return current;
            }
            if (compare < 0) {
                current = current.left;
            }
            if (compare > 0) {
                current = current.right;
            }
        }
        return null;
    }

    public void makeComplete() {
        dsw();
    }

    private void dsw() {
        TreeNode newRoot = new TreeNode(null, null);
        newRoot.right = root;
        treeToVine(newRoot);
        vineToTree(newRoot, numberOfElements);
        this.root = newRoot.right;
    }

    private void treeToVine(TreeNode root) {
        TreeNode tail = root;
        TreeNode rest = tail.right;

        while (rest != null) {
            if (rest.left == null) {
                tail = rest;
                rest = rest.right;
            } else {
                TreeNode temp = rest.left;
                rest.left = temp.right;
                temp.right = rest;
                rest = temp;
                tail.right = temp;
            }
        }
    }

    private void compress(TreeNode root, int count) {
        TreeNode scanner = root;
        for (int i = 0; i < count; i++) {
            TreeNode child = scanner.right;
            scanner.right = child.right;
            scanner = scanner.right;
            child.right = scanner.left;
            scanner.left = child;
        }
    }

    private void vineToTree(TreeNode root, int size) {
        int numberOfLeaves = (int) (size + 1 - Math.pow(2, Math.floor(Math.log(size + 1) / Math.log(2))));
        compress(root, numberOfLeaves);
        size = size - numberOfLeaves;
        while (size > 1) {
            compress(root, (int) Math.floor(size / 2));
            size = (int) Math.floor(size / 2);
        }
    }

    private TreeNode parent(K key) {
        TreeNode parent = null;
        TreeNode current = root;
        int compare;
        while (current != null) {
            compare = key.compareTo(current.key);
            if (compare == 0) {
                return parent;
            }
            parent = current;
            if (compare < 0) {
                current = current.left;
            }
            if (compare > 0) {
                current = current.right;
            }
        }
        return null;
    }

    private class TreeNode {

        K key;
        V value;
        TreeNode left;
        TreeNode right;

        TreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("===============\n");
        System.out.println("elements: " + numberOfElements);
        if (root == null) {
            return result.append("Tree is empty...").toString();
        }

        Queue<TreeNode> q = new ConcurrentLinkedQueue<>();

        q.add(root);

        while (!q.isEmpty()) {
            TreeNode n = q.peek();

            q.remove();
            result.append(String.format(n.key + ": "));
            if (n.left != null) {
                result.append(String.format("| left: " + n.left.key));
                q.add(n.left);
            }
            if (n.right != null) {
                result.append(String.format("| right: " + n.right.key));
                q.add(n.right);
            }

            result.append("\n");
        }

        return result.toString();
    }
}
