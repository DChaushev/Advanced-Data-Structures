/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dimitar
 */
public class SkewHeapTest {

    private SkewHeap heap;
    private List<Integer> list;

    public SkewHeapTest() {
    }

    private void fillList() {
        list.add(19);
        list.add(19);
        list.add(12);
        list.add(-1);
        list.add(4);
        list.add(3);
        list.add(-11);
        list.add(2);
        list.add(-1);
        list.add(3);
        list.add(-10);
    }

    @Before
    public void setUp() {
        heap = new SkewHeap();
        list = new ArrayList<>();
    }

    @After
    public void tearDown() {
        heap = null;
        list = null;
    }

    @Test
    public void addElementsTest() {
        heap.add(4);
        heap.add(10);
        heap.add(5);
        assertEquals(heap.removeMin(), 4);
        heap.add(-2);
        assertEquals(heap.removeMin(), -2);
    }

    @Test
    public void isEmptyTest() {
        heap.add(21);
        assertFalse(heap.empty());
        heap.add(2121);
        assertFalse(heap.empty());
        heap.removeMin();
        heap.removeMin();
        assertTrue(heap.empty());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeMinTest() {
        fillList();
        list.forEach(value -> heap.add(value));
        list.sort(null);

        list.forEach(value -> {
            assertEquals(heap.removeMin(), (int) value);
        });

        heap.removeMin();
    }

    @Test
    public void mergeHeapsTest() {
        fillList();
        SkewHeap heap2 = new SkewHeap();

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                heap.add(list.get(i));
            } else {
                heap2.add(list.get(i));
            }
        }

        heap.merge(heap2);

        list.sort(null);

        list.forEach(value -> {
            assertEquals(heap.removeMin(), (int) value);
        });

    }

}
