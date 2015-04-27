/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Dimitar
 */
public class FunctionalTest {

    private static HashMap<Integer> m;
    private static Method generateHash;
    private static Field MOD;
    private static Field capacity;
    private static final String strings[] = {
        "sdaf af fsad ",
        "asdasf",
        "1231dfsf",
        "asddsf_123asf",
        "MASDASD_123a",
        "george",
        "mitko",
        "peter",
        "ivan peshev",
        "gdfgdh",
        "sadASDas__ sad ",
        "hgj, fmh ff ",
        "Asad, asd adaw",
        "12",
        "12asdasd",
        "d15dfey2",
        "wrht612",
        "1sad2",
        "fg df 12",
        "peter",
        "sadasd peshev",
        "fddfhd",
        "sadAfgjgw235SDas__ sad ",
        "hgj234, fmh ff ",
        "Asa4d, 234asd adaw",
        "1g2342",
        "12afdfh57sdasd",
        "d15df234ey2",
        "wrh 5ue et612",
        "1sa3e ad2",
        "fg d 6aref 12"
    };

    public FunctionalTest() {
    }

    @BeforeClass
    public static void prepareMethods() throws NoSuchMethodException, NoSuchFieldException {
        //METHODS:
        // get generateHash method
        generateHash = HashMap.class.getDeclaredMethod("generateHash", String.class);
        generateHash.setAccessible(true);

        //FIELDS:
        // get mods
        MOD = HashMap.class.getDeclaredField("MOD");
        MOD.setAccessible(true);
        // get capacity
        capacity = HashMap.class.getDeclaredField("capacity");
        capacity.setAccessible(true);
    }

    @Before
    public void fillMap() {
        m = new HashMap<>();

        for (String string : strings) {
            m.insert(string, Integer.SIZE);
        }
    }

    @Test
    public void testHashGeneration() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap m = new HashMap();
        for (String string : strings) {
            int hash = (int) generateHash.invoke(m, string);
            assertTrue(hash >= 0);
            assertTrue(hash <= capacity.getInt(m));
        }
    }

    @Test
    public void testInsertion() {

        for (String string : strings) {
            assertTrue(m.contains(string));
        }
    }

    @Test
    public void testDeletion() {

        assertEquals(m.size(), strings.length - 1);
        for (String string : strings) {
            m.erase(string);
            assertFalse(m.contains(string));
        }
        assertEquals(m.size(), 0);
    }

    @Test
    public void testGet() {

        for (String string : strings) {
            assertEquals(m.get(string), (Object) Integer.SIZE);
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteAndGet() {

        for (String string : strings) {
            m.erase(string);
        }
        for (String string : strings) {
            m.get(string);
        }
    }

    @Test
    public void testClear() {
        m.clear();
        assertEquals(m.size(), 0);
        for (String string : strings) {
            assertFalse(m.contains(string));
        }

        HashMap map = new HashMap(20);

        for (String string : strings) {
            map.insert(string, Integer.MAX_VALUE);
        }

        assertTrue(map.capacity() >= 20);

        map.clear();

        assertFalse(map.capacity() < 20);
        assertTrue(map.size() == 0);

    }

    @Test
    public void testNewConstructorsMin() throws IllegalArgumentException, IllegalAccessException {
        HashMap<Integer> map = new HashMap<>(10);

        assertTrue(capacity.getInt(map) >= 10);

        for (String string : strings) {
            map.insert(string, Integer.SIZE);
        }
        for (String string : strings) {
            map.erase(string);
            assertTrue(capacity.getInt(map) >= 10);
        }
    }

    @Test
    public void testNewConstructorsMax() throws IllegalArgumentException, IllegalAccessException {

        int min = 4;
        int max = 20;

        HashMap<Integer> map = new HashMap<>(min, max);

        assertTrue(map.capacity() >= min);
        assertTrue(map.capacity() <= max);

        for (String string : strings) {
            map.insert(string, Integer.SIZE);
            assertTrue(capacity.getInt(map) >= min);
            assertTrue(map.capacity() <= max);
        }
        System.out.println(map.capacity());
        System.out.println(map.size());
        map.treverse();
        for (String string : strings) {
            map.erase(string);
            assertTrue(capacity.getInt(map) >= min);
            assertTrue(capacity.getInt(map) >= min);
            assertTrue(map.capacity() <= max);
        }
    }

}
