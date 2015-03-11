
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dimitar
 */
public class Main {

    static Random rand = new Random();

    public static void main(String[] args) {

        Treap treap = new TreapImpl();

        treap.insert(6);
        System.out.println(treap.treverse());
        treap.insert(2);
        System.out.println(treap.treverse());
        treap.insert(18);
        System.out.println(treap.treverse());
        treap.insert(-1);
        System.out.println(treap.treverse());
        treap.insert(4);
        System.out.println(treap.treverse());
        treap.insert(10);
        System.out.println(treap.treverse());
        treap.insert(3);
        System.out.println(treap.treverse());
        treap.insert(8);
        System.out.println(treap.treverse());

        treap.remove(6);
        System.out.println(treap.treverse());
//        for (int i = 0; i < 1000; i++) {
//            int k = rand.nextInt(1000);
//            treap.insert(k);
//        }
//        System.out.println(treap.treverse());
    }
}
