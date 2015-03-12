
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

        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        stringTree.insert("mitko");
        stringTree.insert("pesho");
        stringTree.insert("georgi");
        stringTree.insert("ivan");
        stringTree.insert("grozdan");
        stringTree.insert("spas");
        stringTree.insert("asdf");
        stringTree.insert("zadasg");
        stringTree.insert("fdhud");

        System.out.println("Before dsw...");
        System.out.println(stringTree);

        stringTree.dsw();

        System.out.println("After dsw...");
        System.out.println(stringTree);

        BinarySearchTree<Integer> intTree = new BinarySearchTree<>();

        for (int i = 0; i < 30; i++) {
            int k = rand.nextInt(100);
            intTree.insert(k);
        }

        System.out.println("Before dsw...");
        System.out.println(intTree);

        intTree.dsw();

        System.out.println("After dsw...");
        System.out.println(intTree);
    }

}
