/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.advancedDataStructures.suffixArray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Dimitar
 */
public class SuffixArray {

    //This is the array in which we'll keeping all the suffixes /non sorted/
    private final String[] suffixesArray;
    //In this array we are keeping the indices, coresponding to the indices of the suffixes as if they were sorted
    private final Integer[] indicesArray;

    /**
     * The constructor takes some text and builds the suffix array from it. For
     * example - let's say the text is: 'abbracaddabbra'
     *
     * <pre>
     *
     * The suffixes array            And the sorted version looks
     * will look like that:          like that:
     *
     *  0 abbracaddabbra             13 a
     *  1 bbracaddabbra               9 abbra
     *  2 bracaddabbra                0 abbracaddabbra
     *  3 racaddabbra                 4 acaddabbra
     *  4 acaddabbra                  6 addabbra
     *  5 caddabbra                  10 bbra
     *  6 addabbra          ->        1 bbracaddabbra
     *  7 ddabbra                    11 bra
     *  8 dabbra                      2 bracaddabbra
     *  9 abbra                       5 caddabbra
     * 10 bbra                        8 dabbra
     * 11 bra                         7 ddabbra
     * 12 ra                         12 ra
     * 13 a                           3 racaddabbra
     *
     * </pre>
     *
     * In the indicesArray we keep only the sorted indices from the
     * suffixesArray.
     *
     * Having a sorted array we can use binary search!
     *
     * The current time complexity of building the array is O((n^2)*log(n)),
     * where n is the size of the text.
     *
     * If I have time I'll try to implement the DC3 algorithm that builds it for
     * O(n).
     *
     * @param text
     */
    public SuffixArray(String text) {
        suffixesArray = new String[text.length()];
        indicesArray = new Integer[text.length()];
        for (int i = 0; i < suffixesArray.length; i++) {
            suffixesArray[i] = text.substring(i, text.length());
            indicesArray[i] = i;
        }
        Arrays.sort(indicesArray, (x, y) -> suffixesArray[x].compareTo(suffixesArray[y]));
    }

    /**
     * We're binary searching for the lower and upper bound where the pattern
     * matches.
     *
     * If there are such suffixes, we iterate through the interval and add their
     * indexes to the result list.
     *
     * The time complexity is worst case O(n) - that is when the text contains
     * only equal characters (for instance "aaaaaaaa") and we search for that
     * character. In that situation the lowerBound will be 0 and the upper - the
     * length of the text.
     *
     * Summary: We have two binary searches + one iteration through the range:
     * O(2*log(n)) + O(upperBound - lowerBound);
     *
     * @param pattern
     * @return List from all the indices from the original array.
     */
    List<Integer> search(String pattern) {
        List<Integer> result = new LinkedList<>();
        int startIndex = lowerBound(pattern);
        int endIndex = upperBound(pattern);

        if (startIndex != -1 && endIndex != -1) {
            for (int i = startIndex; i < endIndex; i++) {
                result.add(indicesArray[i]);
            }
        }

        return result;
    }

    /**
     * I'm using this method from the Main to get the suffix at a given index.
     *
     * @param index
     * @return
     */
    public String getSuffix(int index) {
        if (index < 0 && index >= suffixesArray.length) {
            throw new IndexOutOfBoundsException();
        }
        return suffixesArray[index];
    }

    private int lowerBound(String pattern) {
        int start = 0;
        int end = indicesArray.length - 1;
        int mid = start + (end - start) / 2;

        while (true) {
            String suffix = suffixesArray[indicesArray[mid]];
            int cmp = suffix.compareTo(pattern);
            if (cmp == 0 || cmp > 0 || suffix.startsWith(pattern)) {
                end = mid - 1;
                if (end < start) {
                    return mid;
                }
            } else {
                start = mid + 1;
                if (end < start) {
                    return mid < indicesArray.length - 1 ? mid + 1 : -1;
                }
            }
            mid = start + (end - start) / 2;
        }
    }

    private int upperBound(String pattern) {
        int start = 0;
        int end = indicesArray.length - 1;
        int mid = (start + end) / 2;
        while (true) {
            String suffix = suffixesArray[indicesArray[mid]];
            int cmp = suffix.compareTo(pattern);
            if (cmp == 0 || cmp < 0 || suffix.startsWith(pattern)) {
                start = mid + 1;
                if (end < start) {
                    return mid < indicesArray.length ? mid + 1 : -1;
                }
            } else {
                end = mid - 1;
                if (end < start) {
                    return mid;
                }
            }
            mid = (start + end) / 2;
        }
    }
}
