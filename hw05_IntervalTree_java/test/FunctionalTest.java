/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Dimitar
 */
public class FunctionalTest {

    private static Method getNextNumberPowerOfTwo;
    private static final int[] treeArray1 = new int[]{42, 17, 13, 22, 10, 31, 29, 8, 15, 20, 42, 11, 3};
    private static final int[] treeArray2 = new int[]{1, 3, 5, 8, -2, 7, 15};
    private static final int[] treeArray3 = new int[]{4, 1, 3, 4, 8, -20, 15, -10};
    private static final int[] treeArray4 = new int[]{40, 5, -10, 12, 30, 4, 1};
    private static final int[] treeArray5 = new int[]{12, 64, -100, -12, -30, -4, -7};

    public FunctionalTest() {
    }

    @BeforeClass
    public static void prepareMethods() throws NoSuchMethodException {
        getNextNumberPowerOfTwo = IntervalTree.class.getDeclaredMethod("getNextNumberPowerOfTwo", Integer.class);
        getNextNumberPowerOfTwo.setAccessible(true);
    }

    @Test
    public void testPowerOfTwoNumberGeneration() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        IntervalTree t = new IntervalTree(new int[0]);

        assertEquals(getNextNumberPowerOfTwo.invoke(t, 0), 0);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 1), 2);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 3), 4);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 5), 8);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 8), 8);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 10), 16);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 20), 32);
        assertEquals(getNextNumberPowerOfTwo.invoke(t, 60), 64);
    }

    @Test
    public void testUpdate() {

        IntervalTree tree = new IntervalTree(treeArray3);
        tree.update(1, 5, 5);

        tree = new IntervalTree(treeArray4);
        tree.update(2, 5, 10);

        tree = new IntervalTree(treeArray2);
        tree.update(0, 7, 1);

        tree = new IntervalTree(treeArray1);
        tree.update(2, 10, 10);

    }

    @Test
    public void updateAndQuery() {

        IntervalTree tree = new IntervalTree(treeArray3);

        tree.update(1, 6, 10);
        tree.query(0, 7);
        assertEquals(tree.query(0, 7), 25);
        assertEquals(tree.query(1, 6), 25);
        assertEquals(tree.query(4, 4), 18);

        tree = new IntervalTree(treeArray1);
        tree.update(5, 7, 5000);
        assertEquals(tree.query(0, 15), 5031);
        assertEquals(tree.query(2, 6), 5031);
        assertEquals(tree.query(0, 1), 42);

        tree.update(0, 15, 6000);
        assertEquals(tree.query(0, 15), 5031 + 6000);

        tree = new IntervalTree(treeArray1);
        tree.update(0, 2, 50);
        tree.update(10, 15, 50);
        assertEquals(tree.query(5, 9), 31);
        assertEquals(tree.query(0, 9), 92);
        assertEquals(tree.query(6, 11), 92);
    }

    @Test
    public void testQuery() {
        IntervalTree tree = new IntervalTree(treeArray1);

        assertEquals(tree.query(0, 10), 42);
        assertEquals(tree.query(3, 7), 31);
        assertEquals(tree.query(6, 9), 29);
        assertEquals(tree.query(1, 12), 42);
        assertEquals(tree.query(0, 15), 42);
        assertEquals(tree.query(13, 15), 0);

        tree = new IntervalTree(treeArray1);
        tree.update(13, 15, 1);
        assertEquals(tree.query(12, 15), 3);

        tree = new IntervalTree(treeArray5);
        assertEquals(tree.query(5, 5), -4);
        assertEquals(tree.query(6, 6), -7);
        assertEquals(tree.query(0, 7), 64);
        tree.update(2, 7, 100);
        assertEquals(tree.query(2, 2), 0);
        assertEquals(tree.query(0, 6), 96);
        assertEquals(tree.query(0, 7), 100);
    }
}
