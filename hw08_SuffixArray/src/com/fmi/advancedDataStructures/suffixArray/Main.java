/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.advancedDataStructures.suffixArray;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Dimitar
 */
public class Main {

    public static void main(String[] args) {
        testSearch();
        System.out.println("\n==================\nStress test:");
        stressTest();
    }

    private static void findSuffix(SuffixArray sa, String suffix, String text) {
        System.out.println("=======================");
        System.out.print("Searching for '" + suffix + "': \nFound at indices:\n");
        List<Integer> result;
        result = sa.search(suffix);
        Collections.sort(result);
        if (!result.isEmpty()) {
            for (Integer i : result) {
                System.out.println(i + ": " + text.substring(i));
            }
        }
    }

    private static void testSearch() {
        final String ABBRACADDABBRA = "abbracaddabbra";
        final String AAA = "aaaaaaaaaaaaaaaa";
        final String LOREM_IPSUM = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                + " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
                + "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
                + "It has survived not only five centuries, but also the leap into electronic typesetting, "
                + "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset "
                + "sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus "
                + "PageMaker including versions of Lorem Ipsum."
                + "It is a long established fact that a reader will be distracted by the readable content of a page when "
                + "looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of "
                + "letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop "
                + "publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for "
                + "'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, "
                + "sometimes by accident, sometimes on purpose (injected humour and the like).";

        SuffixArray sa = new SuffixArray(ABBRACADDABBRA);
        findSuffix(sa, "abbra", ABBRACADDABBRA);
        findSuffix(sa, "a", ABBRACADDABBRA);
        findSuffix(sa, "bb", ABBRACADDABBRA);
        findSuffix(sa, "bbra", ABBRACADDABBRA);
        findSuffix(sa, "ra", ABBRACADDABBRA);
        findSuffix(sa, "xyz", ABBRACADDABBRA);
        findSuffix(sa, "ghost", ABBRACADDABBRA);

        sa = new SuffixArray(LOREM_IPSUM.toLowerCase());
        findSuffix(sa, "lorem", LOREM_IPSUM);
        findSuffix(sa, "ipsum", LOREM_IPSUM);
        findSuffix(sa, "version", LOREM_IPSUM);
        findSuffix(sa, "ghost", LOREM_IPSUM);
        findSuffix(sa, "is", LOREM_IPSUM);
        findSuffix(sa, "that", LOREM_IPSUM);
        findSuffix(sa, "the 1", LOREM_IPSUM);

        sa = new SuffixArray(AAA);
        findSuffix(sa, "a", AAA);
        findSuffix(sa, "aaa", AAA);
        findSuffix(sa, "aaaaaaa", AAA);
        findSuffix(sa, "b", AAA);
        findSuffix(sa, "ab", AAA);
    }

    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789"
            + " `~!@#$%^&*()-_=+{}[]|\\'\",.<>/?:;";

    static String randomString(int len) {
        Random rand = new Random();
        StringBuilder str = new StringBuilder(1_000_000);
        for (int c = 0; c < len; c++) {
            str.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        return str.toString();
    }

    private static void stressTest() {
        int size = 1_000_000;
        String bigText = randomString(size);

        System.out.print("build array with text with size " + size);

        long startTime = System.currentTimeMillis();
        SuffixArray sa = new SuffixArray(bigText);
        long endTime = System.currentTimeMillis();

        String suff = "a";
        System.out.println(" for " + (endTime - startTime) + " ms.");
        System.out.println("Searching for '" + suff + "':");

        startTime = System.currentTimeMillis();
        List res = sa.search(suff);
        endTime = System.currentTimeMillis();

        System.out.println(res.size() + " suffixes found for " + (endTime - startTime) + " ms.");
    }

}
