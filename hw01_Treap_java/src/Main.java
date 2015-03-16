
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

    private static void stressInsert(int n){
        Treap treap = new TreapImpl();
        System.out.println(String.format("Inserting %d elements: ", n));
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            treap.insert(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
    }
    
    public static void main(String[] args) {

        Treap treap = new TreapImpl();

        treap.insert(6);
        System.out.println(treap);
        treap.insert(2);
        System.out.println(treap);
        treap.insert(18);
        System.out.println(treap);
        treap.insert(-1);
        System.out.println(treap);
        treap.insert(4);
        System.out.println(treap);
        treap.insert(10);
        System.out.println(treap);
        treap.insert(3);
        System.out.println(treap);
        treap.insert(8);
        System.out.println(treap);
        
        System.out.println(treap.containsKey(6));
        treap.remove(6);
        System.out.println(treap);
        System.out.println(treap.containsKey(6));
        
        stressInsert(2_000_000);
        stressInsert(4_000_000);
        stressInsert(8_000_000);
        stressInsert(16_000_000);
        stressInsert(32_000_000);
        
    }
}
