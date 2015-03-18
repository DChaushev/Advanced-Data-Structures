
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
    
    static Random rand = new Random(1);
    
    public static void main(String[] args) {
        SplayTreeInterface tree = new SplayTree();
        for (int i = 0; i < 10; i++) {
            int k = rand.nextInt(20);
            System.out.println("---inserting: " + k);
            tree.insert(i);
        }
        for (int i = 20; i >= 10; i--) {
            tree.insert(i);
        }
        System.out.println(tree);
        tree.contains(8);
        System.out.println(tree);
        tree.delete(7);
        System.out.println(tree);
    }
}
