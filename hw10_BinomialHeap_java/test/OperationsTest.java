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
    public void testFindMin(){
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
    
}
