
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author grade
 */
public class Main {

    static Random rand = new Random();

    public static void main(String[] args) {

        BinarySearchTree<String, Integer> stringTree = new BinarySearchTree<>();

        stringTree.insert("mitko", 1);
        stringTree.insert("pesho", 2);
        stringTree.insert("georgi", 3);
        stringTree.insert("ivan", 4);
        stringTree.insert("grozdan", 5);
        stringTree.insert("spas", 6);
        stringTree.insert("asdf", 7);
        stringTree.insert("zadasg", 8);
        stringTree.insert("fdhud", 9);
        stringTree.insert("dsa", 9);

        System.out.println("Before dsw...");
        System.out.println(stringTree);

        stringTree.makeComplete();

        System.out.println("After dsw...");
        System.out.println(stringTree);

        BinarySearchTree<Integer, Integer> intTree = new BinarySearchTree<>();

        for (int i = 0; i < 60; i++) {
            int k = rand.nextInt(100);
            intTree.insert(k, k + 1);
        }

        System.out.println("Before dsw...");
        System.out.println(intTree);

        intTree.makeComplete();

        System.out.println("After dsw...");
        System.out.println(intTree);

        System.out.println(intTree.contains(10));
        System.out.println(stringTree.contains("mitko"));
        System.out.println(stringTree.get("mitko"));
        System.out.println(intTree.get(19));

        stringTree.remove("mitko");
        System.out.println(stringTree);
    }

}
