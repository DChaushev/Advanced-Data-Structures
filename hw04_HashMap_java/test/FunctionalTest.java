/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    }

    @Test//(expected = NoSuchElementException.class)
    public void testSpeed() throws IOException {
        List<String> lines = getLines();

        System.out.println(lines.size() + " words");

        HashMap<Integer> myMap = new HashMap<>();
        Map<String, Integer> javaMap = new java.util.HashMap<>();

        long startTime = System.currentTimeMillis();

        lines.stream().forEach((string) -> {
            myMap.insert(string, Integer.SIZE);
        });
        lines.stream().forEach((string) -> {
            myMap.get(string);
        });
        lines.stream().forEach((string) -> {
            myMap.erase(string);
        });

        long endTime = System.currentTimeMillis();

        System.out.println("myMap time: " + (endTime - startTime));

        startTime = System.currentTimeMillis();

        lines.stream().forEach((string) -> {
            javaMap.put(string, Integer.SIZE);
        });
        lines.stream().forEach((string) -> {
            javaMap.get(string);
        });
        lines.stream().forEach((string) -> {
            javaMap.remove(string);
        });
        endTime = System.currentTimeMillis();

        System.out.println("javaMap time: " + (endTime - startTime));
        System.out.println("---------------------------");
    }

    @Test
    public void stressTest() throws IOException {
        for (int i = 40; i > 0; i--) {
            runSpeedTest(i);
        }
        System.out.println("---------------------------");
    }

    private List<String> getLines() throws IOException {
        List<String> lines = new ArrayList<>();

        Files.lines(Paths.get("./test/words.txt")).forEach(line -> {
            lines.add(line);
        });

        return lines;
    }

    private void runSpeedTest(int n) throws IOException {
        HashMap<Integer> myMap = new HashMap<>();
        List<String> lines = getLines();
        int element = 0;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < lines.size() / n; i++) {
            myMap.insert(lines.get(i), Integer.SIZE);
        }
        element = myMap.size();
        for (int i = 0; i < lines.size() / n; i++) {
            myMap.get(lines.get(i));
        }
        for (int i = 0; i < lines.size() / n; i++) {
            myMap.erase(lines.get(i));
        }

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("%d elements %d time", element, (endTime - startTime)));
    }

}
