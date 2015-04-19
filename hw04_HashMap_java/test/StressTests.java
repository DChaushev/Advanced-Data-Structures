/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dimitar
 */
public class StressTests {
    
    public StressTests() {
    }
    
    @Test//(expected = NoSuchElementException.class)
    public void testSpeed() throws IOException {
        List<String> lines = getLines();

        System.out.println(lines.size() + " words");

        HashMap<String, Integer> myMap = new HashMap<>();
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
        HashMap<String, Integer> myMap = new HashMap<>();
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

    private final String ALPHABET[] = {
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
        ".", "_", "-", "_", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    @Test
    public void nikiTest() {
        int numberOfElements = 1_000_000;
        test(numberOfElements);
    }

    private void test(int numberOfElements) {
        int size = 0;
        Random rand = new Random();
        HashMap<String, Integer> map = new HashMap<>();
        java.util.Map<String, Integer> buildMap = new java.util.HashMap<>();

        String[] elements = new String[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            elements[i] = getRandomString(rand.nextInt(20) + 5);
        }
        System.out.println("finished building elements");

        long start = System.currentTimeMillis();
        for (String str : elements) {
            map.insert(str, Integer.MAX_VALUE);
        }
        size = map.size();
        for (String str : elements) {
            map.get(str);
        }
        for (String str : elements) {
            map.erase(str);
        }
        long end = System.currentTimeMillis();
        System.out.println("My HashMap:  " + size + " elements, " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (String str : elements) {
            buildMap.put(str, Integer.MAX_VALUE);
        }
        size = buildMap.size();
        for (String str : elements) {
            buildMap.get(str);
        }
        for (String str : elements) {
            buildMap.remove(str);
        }
        end = System.currentTimeMillis();
        System.out.println("Java HashMap:  " + size + " elements, " + (end - start) + " ms");
    }

    private String getRandomString(int length) {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(ALPHABET[rand.nextInt(ALPHABET.length)]);
        }

        return result.toString();
    }

    @Test
    public void testIntAsKey() {
        HashMap<Integer, Integer> map = new HashMap<>();

        int N = 1_000_000;
        int size = 0;

        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            map.insert(i, i);
        }
        size = map.size();
        for (int i = 0; i < N; i++) {
            assertTrue(map.contains(i));
        }
        for (int i = 0; i < N; i++) {
            map.erase(i);
        }
        for (int i = 0; i < N; i++) {
            assertFalse(map.contains(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("Elements: " + size + ", time: " + (end - start));
    }
}
