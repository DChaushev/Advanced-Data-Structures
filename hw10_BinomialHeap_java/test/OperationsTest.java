/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import binomial_heap.BinomialHeap;
import binomial_heap.BinomialHeapInterface;
import org.junit.Test;
import static org.junit.Assert.*;
import binomial_heap.BinomialHeapInterface.Node;

/**
 *
 * @author Dimitar
 */
public class OperationsTest {

    public OperationsTest() {
    }

    @Test
    public void testFindMin() {
        Node root = new Node(10);

        BinomialHeapInterface bh = new BinomialHeap();
        assertEquals(bh.getMin(), null);

        bh = new BinomialHeap(root);
        assertEquals(bh.getMin(), 10);

        root.right = new Node(2);
        assertEquals(bh.getMin(), 2);

        root.right.right = new Node(21);
        root.right.right.right = new Node(1);
        assertEquals(bh.getMin(), 1);

    }

    @Test
    public void toBinaryTest() {
        String i7 = Integer.toBinaryString(7);
        String i13 = Integer.toBinaryString(13);

        i7 = String.format("%0" + 1 + "d" + i7, 0);

        System.out.println(i7);
        System.out.println(i13);
    }

    @Test
    public void consolidateTest() {
        Node l = new Node(0);
        Node ll = l;
        for (int i = 1; i < 3; i++) {
            l.right = new Node(i);
            l = l.right;
        }

        Node r = new Node(3);
        Node rr = r;
        for (int i = 4; i < 6; i++) {
            r.right = new Node(i);
            r = r.right;
        }

        Node root = new BinomialHeap().consolidate(ll, 7, rr, 13);

        while (root != null) {
            System.out.println(root.key);
            root = root.right;
        }

    }

}
