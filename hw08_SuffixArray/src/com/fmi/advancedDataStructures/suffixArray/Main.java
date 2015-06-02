/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.advancedDataStructures.suffixArray;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Dimitar
 */
public class Main {

    public static void main(String[] args) {
        testSearch();
    }

    private static void findSuffix(SuffixArray sa, String suffix) {
        System.out.println("=======================");
        System.out.print("Searching for '" + suffix + "': \nFound at indices:\n");
        List<Integer> result;
        result = sa.search(suffix);
        Collections.sort(result);
        if (!result.isEmpty()) {
            for (Integer i : result) {
                System.out.println(i + ": " + sa.getSuffix(i));
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
        findSuffix(sa, "abbra");
        findSuffix(sa, "a");
        findSuffix(sa, "bb");
        findSuffix(sa, "bbra");
        findSuffix(sa, "ra");
        findSuffix(sa, "xyz");
        findSuffix(sa, "ghost");

        sa = new SuffixArray(LOREM_IPSUM.toLowerCase());
        findSuffix(sa, "lorem");
        findSuffix(sa, "ipsum");
        findSuffix(sa, "version");
        findSuffix(sa, "ghost");
        findSuffix(sa, "is");
        findSuffix(sa, "that");
        findSuffix(sa, "the 1");

        sa = new SuffixArray(AAA);
        findSuffix(sa, "a");
        findSuffix(sa, "aaa");
        findSuffix(sa, "aaaaaaa");
        findSuffix(sa, "b");
        findSuffix(sa, "ab");
    }

}
