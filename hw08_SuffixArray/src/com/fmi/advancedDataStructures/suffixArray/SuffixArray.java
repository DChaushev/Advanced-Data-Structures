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
     * We're binary searching for a given suffix in the array with the sorted
     * indices. If there's no such element - it returns -1.
     *
     * Else it returns the position /from the sorted array/ of the first match.
     * After that we check it's neighbors for more string with that suffix.
     *
     * @param pattern
     * @return List from all the indices from the original array.
     */
    List<Integer> search(String pattern) {
        List<Integer> result = new LinkedList<>();
        int index = binarySearchForIndex(pattern);
        if (index != -1) {
            result.add(indicesArray[index]);
            int i = index - 1;
            while (i >= 0 && suffixesArray[indicesArray[i]].startsWith(pattern)) {
                result.add(indicesArray[i]);
                i--;
            }
            i = index + 1;
            while (i < indicesArray.length && suffixesArray[indicesArray[i]].startsWith(pattern)) {
                result.add(indicesArray[i]);
                i++;
            }
        }
        return result;
    }

    /**
     * Needles to say what this does.
     *
     * @see #search(java.lang.String)
     *
     * @param pattern
     * @return
     */
    private int binarySearchForIndex(String pattern) {
        int start = 0;
        int end = indicesArray.length;

        while (end >= start) {
            int mid = start + (end - start) / 2;
            String suffix = suffixesArray[indicesArray[mid]];
            if (suffix.startsWith(pattern)) {
                return mid;
            } else if (suffix.compareTo(pattern) < 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
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
}
