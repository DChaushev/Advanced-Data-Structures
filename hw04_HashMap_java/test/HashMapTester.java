/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dimitar
 */
public class HashMapTester {

    public HashMapTester() {
    }

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789"
            + " `~!@#$%^&*()-_=+{}[]|\\'\",.<>/?:;";

    private String randomString() {
        Random rand = new Random();
        int len = 3 + rand.nextInt(8);
        StringBuilder str = new StringBuilder();
        for (int c = 0; c < len; c++) {
            str.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        return str.toString();
    }

    private void testInitialCapacity(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testInitialCapacity()");

        assertTrue(hmap.size() == 0);
        assertTrue(tmap.isEmpty());
        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
    }

    private void testInsert(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testInsert()");

        hmap.insert("espr1t", 42);
        tmap.put("espr1t", 42);

        assertTrue(hmap.size() == 1);
        assertTrue(tmap.size() == 1);
        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
    }

    private void testContainsAndGet(HashMap<Integer> hmap, Map<String, Integer> tmap) {
        System.out.println("In testContainsAndGet()");

        assertTrue(hmap.contains("espr1t"));
        assertTrue(tmap.containsKey("espr1t"));

        assertTrue(hmap.get("espr1t") == 42);
        assertTrue(tmap.get("espr1t") == 42);

        assertTrue(!hmap.contains("ThinkCreative"));
        assertTrue(!tmap.containsKey("ThinkCreative"));
    }

    private void testMoreOperations(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testMoreOperations()");

        hmap.insert("exod40", 13);
        tmap.put("exod40", 13);
        assertTrue(hmap.size() == 2);
        assertTrue(tmap.size() == 2);

        hmap.insert("espr1t", 666);
        tmap.put("espr1t", 666);
        assertTrue(hmap.size() == 2);
        assertTrue(tmap.size() == 2);

        assertTrue(hmap.get("espr1t") == 666);
        assertTrue(tmap.get("espr1t") == 666);
        assertTrue(hmap.get("exod40") == 13);
        assertTrue(tmap.get("exod40") == 13);

        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
    }

    private void testClear(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testClear()");

        hmap.clear();
        tmap.clear();
        assertTrue(hmap.size() == 0);
        assertTrue(tmap.isEmpty());

        assertTrue(!hmap.contains("espr1t"));
        assertTrue(!hmap.contains("exod40"));

        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
    }

    private void testAddingAfterClear(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testAddingAfterClear()");

        String ratingKey[] = {"Petr", "tourist", "Egor", "peter50216", "bmerry", "ACRush", "wata", "tomek", "WJMZBMR", "lyrically"};
        Integer ratingValue[] = {3792, 3758, 3427, 3346, 3332, 3298, 3279, 3204, 3189, 3151};

        for (int i = 0; i < ratingKey.length; i++) {
            hmap.insert(ratingKey[i], ratingValue[i]);
            tmap.put(ratingKey[i], ratingValue[i]);
        }

        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
    }

    private void testChangingAValue(HashMap<Integer> hmap, Map<String, Integer> tmap) {
        System.out.println("In testChangingAValue()");

        assertTrue(hmap.size() == tmap.size());
        assertTrue(hmap.get("tomek") == 3204);
        hmap.insert("tomek", 1337);
        assertTrue(hmap.get("tomek") == 1337);
        hmap.insert("tomek", 3204);
    }

    private void testErase(HashMap<Integer> hmap, Map<String, Integer> tmap) {
        System.out.println("In testErase()");

        hmap.erase("peter50216");
        tmap.remove("peter50216");

        assertTrue(hmap.size() == tmap.size());
        hmap.erase("not existing");
        assertTrue(hmap.size() == tmap.size());

        hmap.erase("ACRush");
        tmap.remove("ACRush");
        assertTrue(hmap.size() == 8);
    }

    private void testOverwrite(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testOverwrite()");

        String ratingKey[] = {"Petr", "tourist", "Egor", "peter50216", "bmerry", "ACRush", "wata", "tomek", "WJMZBMR", "lyrically"};
        Integer ratingValue[] = {3792, 3758, 3427, 3346, 3332, 3298, 3279, 3204, 3189, 3151};

        for (int i = 0; i < ratingKey.length; i++) {
            hmap.insert(ratingKey[i], ratingValue[i]);
            tmap.put(ratingKey[i], ratingValue[i]);
        }

        // Overwrite to see if the load factor remains okay
        for (int i = 0; i < ratingKey.length; i++) {
            hmap.insert(ratingKey[i], ratingValue[i]);
            tmap.put(ratingKey[i], ratingValue[i]);
        }

        assertTrue(hmap.size() == 10);
        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));

        hmap.insert("espr1t", 42);
        tmap.put("espr1t", 42);
        hmap.insert("exod40", 1337);
        tmap.put("exod40", 1337);
        assertTrue(hmap.size() == 12);
        assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
    }

    private void testAccessingInvalidKey(HashMap<Integer> hmap, Map<String, Integer> tmap) {
        System.out.println("In testAccessingInvalidKey()");

        assertTrue(!tmap.containsKey("non existing"));

        boolean errorThrown = false;
        try {
            hmap.get("non existing");
        } catch (Exception e) {
            errorThrown = true;
        }
        assertTrue(errorThrown);
    }

    private void testAutomaticResizing(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testAutomaticResizing()");
        Random rand = new Random();

        // Test automatic resizing
        for (int i = 0; i < 10000; i++) {
            String str = randomString();
            int value = rand.nextInt(1000000007);
            hmap.insert(str, value);
            tmap.put(str, value);
            assertTrue(hmap.size() == (int) tmap.size());
            assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
        }

        // Check maps consistency
        assertTrue(hmap.size() == (int) tmap.size());
        for (Map.Entry<String, Integer> it : tmap.entrySet()) {
            if (!it.getValue().equals(hmap.get(it.getKey()))) {
                System.err.format("  -- for string %s: %d vs %d\n",
                        it.getKey(), it.getValue(), hmap.get(it.getKey()));
            }
            assertTrue(Objects.equals(it.getValue(), hmap.get(it.getKey())));
        }
    }

    private void testMaintainingLoadFactor(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCap, int maxCap) {
        System.out.println("In testMaintainingLoadFactor()");
        Random rand = new Random();

        while (hmap.size() > 0) {
            String str = randomString();
            if (rand.nextInt(10) == 0) {
                hmap.erase(str);
                tmap.remove(str);
            } else {
                String key = "";
                int which = rand.nextInt(tmap.size());
                for (Map.Entry<String, Integer> it : tmap.entrySet()) {
                    if (which == 0) {
                        key = it.getKey();
                        break;
                    }
                    which--;
                }
                hmap.erase(key);
                tmap.remove(key);
            }
            assertTrue(hmap.capacity() <= Math.min(Math.max((hmap.size() + 1) * 4, minCap), maxCap));
        }
    }

    private void funcTest(HashMap<Integer> hmap, Map<String, Integer> tmap, int minCapacity, int maxCapacity) {
        System.out.format("Starting functional test with capacity range [%d, %d].\n",
                minCapacity, maxCapacity);

        testInitialCapacity(hmap, tmap, minCapacity, maxCapacity);
        testInsert(hmap, tmap, minCapacity, maxCapacity);
        testContainsAndGet(hmap, tmap);
        testMoreOperations(hmap, tmap, minCapacity, maxCapacity);
        testClear(hmap, tmap, minCapacity, maxCapacity);
        testAddingAfterClear(hmap, tmap, minCapacity, maxCapacity);
        testChangingAValue(hmap, tmap);
        testErase(hmap, tmap);
        testOverwrite(hmap, tmap, minCapacity, maxCapacity);
        testAccessingInvalidKey(hmap, tmap);
        testAutomaticResizing(hmap, tmap, minCapacity, maxCapacity);
        testMaintainingLoadFactor(hmap, tmap, minCapacity, maxCapacity);

        System.out.println();
    }

    @Test
    public void simpleTest1() {
        // Default constructor
        HashMap<Integer> hmap1 = new HashMap<>();
        Map<String, Integer> tmap1 = new java.util.HashMap<>();
        funcTest(hmap1, tmap1, 0, 1000000000);
    }

    @Test
    public void simpleTest2() {

        // Constructor with fixed lower number of buckets
        HashMap<Integer> hmap2 = new HashMap<>(1337);
        Map<String, Integer> tmap2 = new java.util.HashMap<>();
        funcTest(hmap2, tmap2, 1337, 1000000000);
    }

    @Test
    public void simpleTest3() {

        // Constructor with fixed lower and upper number of buckets
        HashMap<Integer> hmap3 = new HashMap<>(1337, 42000);
        Map<String, Integer> tmap3 = new java.util.HashMap<>();
        funcTest(hmap3, tmap3, 1337, 42000);
    }

}
