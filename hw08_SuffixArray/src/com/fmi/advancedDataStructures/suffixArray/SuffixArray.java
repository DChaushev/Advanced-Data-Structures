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
 * ATTENTION: The code below is mighty ugly!!!
 *
 * But is reasonably fast.
 *
 * @author Dimitar
 */
public class SuffixArray {

    /*
     I was keeping all the suffixes in an array, but decided that it was taking too
     much memory. So now I'm keeping the whole text and when a need a suffix I call
     String.substring()
     */
    private final String text;
    /*
     In this array we are keeping the indices, coresponding to the indices of
     the suffixes as if they were sorted.
     */
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

        this.text = text;
        indicesArray = new Integer[text.length()];
        for (int i = 0; i < indicesArray.length; i++) {
            indicesArray[i] = i;
        }
        Arrays.sort(indicesArray, (x, y) -> compare(x, y));
    }

    /**
     * We're binary searching for the lower and upper bound where the pattern
     * matches.
     *
     * If there are such suffixes, we iterate through the interval and add their
     * indexes to the result list.
     *
     * The worst case scenario is when the text contains only equal characters
     * (for instance "aaaaaaaa") and we search for that character. In that
     * situation the lowerBound will be 0 and the upper - the length of the
     * text.
     *
     * Summary: We have two binary searches + one iteration through the range:
     * O(m*log(n)) + O(upperBound - lowerBound), where m is the length of the
     * pattern.
     *
     * @param pattern
     * @return List from all the indices from the original array.
     */
    public List<Integer> search(String pattern) {
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
     * Binary searches for the first appearances of the pattern.
     *
     * @param pattern
     * @return
     */
    private int lowerBound(String pattern) {
        int start = 0;
        int end = indicesArray.length - 1;
        int mid = start + (end - start) / 2;

        while (true) {
            int cmp = compareToPattern(indicesArray[mid], pattern);
            if (cmp == 0 || cmp > 0 || startsWith(indicesArray[mid], pattern)) {
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

    /**
     * Binary searches for the last appearances of the pattern.
     *
     * @param pattern
     * @return
     */
    private int upperBound(String pattern) {
        int start = 0;
        int end = indicesArray.length - 1;
        int mid = start + (end - start) / 2;

        while (true) {
            int cmp = compareToPattern(indicesArray[mid], pattern);
            if (cmp == 0 || cmp < 0 || startsWith(indicesArray[mid], pattern)) {
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
            mid = start + (end - start) / 2;
        }
    }

    /**
     * Below I'm defining 3 functions.
     *
     * I'm doing all this just to avoid using substring, which from Java 7 is
     * making a copy of the substring and is very slow.
     *
     * Just for comparison - while using substring the building of an array with
     * 1000000 elements took more than a minute.
     *
     * Now it takes less than a sec!
     *
     *
     * This first one compares two substring starting position x and y in the
     * text.
     *
     * @param x
     * @param y
     * @return
     */
    private int compare(int x, int y) {
        int len1 = text.length() - x;
        int len2 = text.length() - y;
        int lim = Math.min(len1, len2);

        int k = 0;
        while (k < lim) {
            char c1 = text.charAt(x++);
            char c2 = text.charAt(y++);
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }

    /**
     * This compares a given substring from the text with a given pattern.
     *
     * @param index
     * @param pattern
     * @return
     */
    private int compareToPattern(int index, String pattern) {
        int len1 = text.length() - index;
        int len2 = pattern.length();
        int lim = Math.min(len1, len2);

        int k = 0;
        while (k < lim) {
            char c1 = text.charAt(index++);
            char c2 = pattern.charAt(k++);
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    }

    /**
     * Checks if a given substring from the text starts with a given pattern.
     *
     * @param index
     * @param pattern
     * @return
     */
    private boolean startsWith(int index, String pattern) {
        if (text.length() - index < pattern.length()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != text.charAt(index + i)) {
                return false;
            }
        }
        return true;
    }
}
