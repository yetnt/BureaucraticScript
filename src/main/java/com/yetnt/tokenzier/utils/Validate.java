package com.yetnt.tokenzier.utils;

import com.yetnt.lang.Chars;
import com.yetnt.utils.SamePair;

import java.util.ArrayList;

public class Validate {

    /**
     * Simple Check.
     * Checks if a character is considered an operator.
     * <p>
     *
     * @param c The character
     * @return boolean indicating whether the given char is a string.
     */
    public static boolean isOperator(char c) {
        return Chars.Operators.getAllChars().contains(c);
    }

    /**
     * Checks if the given character array contains any arithmetic or boolean
     * operators.
     * Returns 1 if an arithmetic operator is found, 0 if a boolean operator is
     * found, and -1 if no operators are found.
     *
     * @param string Input
     * @return Index of operator
     */
    public static int containsOperator(char[] string) {
        for (char c : string) {
            int t = Chars.Operators.getType(c + "");
            if (t == -1)
                continue;
            else
                return t;
        }
        return -1;
    }


    /**
     * General method to check whether `opIndex` is within the range of any one of
     * the Tuple2 pairs in the `list`
     * <p>
     * Currently used by {@link Validate#isOpInQuotePair(String, int)}
     * <p>
     * A given `index` is considered enclosed within the set of pairs if it is
     * within the domain `(first, second)` (Both numbers exclusive, as the index
     * you're looking for cannot also be part of the pair)
     *
     * @param index The index to search for
     * @param list  An arraylist of Tuple2 objects containing the pairs of ranges.
     *              Generally you can make this list by calling either
     *              {@link Find#quotationPairs(String)} or
     *              {@link Find#bracePairs(String)}
     * @return The index of the list in which the `index` was found to be in range.
     *          Otherwise `-1`
     */
    public static int isOpInPair(int index, ArrayList<SamePair<Integer>> list) {
        for (int i = 0; i < list.size(); i++) {
            SamePair<Integer> tuple2 = list.get(i);
            if (index > tuple2.first() && index < tuple2.second())
                return i;

        }
        return -1;
    }

    /**
     * Checks if the given operator index is within any pair of quotation marks in
     * the provided line.
     *
     * @param line    The input string to be analyzed.
     * @param opIndex The index of the operator to check within the line.
     * @return The index of the quotation pair (0-based) that contains the operator
     *         index,
     *         or -1 if the operator index is not within any quotation pair.
     */
    public static int isOpInQuotePair(String line, int opIndex) {
        ArrayList<SamePair<Integer>> quotePairs = Find.quotationPairs(line);
        return isOpInPair(opIndex, quotePairs);
    }
}
