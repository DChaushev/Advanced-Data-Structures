
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
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable> {

    private TreeNode root;
    private int numberOfElements;

    public BinarySearchTree() {
        root = null;
        numberOfElements = 0;
    }

    public void insert(T data) {
        insert(root, data, root);
    }

    private void insert(TreeNode root, T data, TreeNode parent) {
        if (root == null) {
            root = new TreeNode(data);
            numberOfElements++;
            if (this.root == null) {
                this.root = root;
            } else {
                if (parent != null) {
                    if (parent.data.compareTo(root.data) > 0) {
                        parent.left = root;
                    } else {
                        parent.right = root;
                    }
                }
            }
            root.parent = parent;
        } else if (data.compareTo(root.data) < 0) {
            insert(root.left, data, root);
        } else if (data.compareTo(root.data) > 0) {
            insert(root.right, data, root);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("===============\n");

        Queue<TreeNode> q = new ConcurrentLinkedQueue<>();

        q.add(root);

        while (!q.isEmpty()) {
            TreeNode n = q.peek();

            q.remove();
            result.append(String.format(n.data + ": "));
            if (n.left != null) {
                result.append(String.format("| left: " +  n.left.data));
                q.add(n.left);
            }
            if (n.right != null) {
                result.append(String.format("| right: " + n.right.data));
                q.add(n.right);
            }

            result.append("\n");
        }

        return result.toString();
    }

    public void dsw() {
        TreeNode newRoot = new TreeNode(null);
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

    private class TreeNode{

        T data;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        TreeNode(T data) {
            this.data = data;
            left = null;
            right = null;
            parent = null;
        }
    }

}
