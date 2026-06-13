package com.yetnt.tokenzier.utils;

import com.yetnt.lang.Chars;
import com.yetnt.utils.Pair;
import com.yetnt.utils.SamePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Find {

    public static class LeastImportantOperator {
        public String op;
        public int index;
        public int tStatementType;

        public LeastImportantOperator(String op, int index, int group) {
            this.op = op;
            this.index = index;
            switch (group) {
                case 0:// Exponentiation
                case 1:// DivMult
                case 2:// AddSub
                case 3:// Bitwise shifts, Also handled within number handling.
                case 4:// Bitwise operations. Normally this should be by itself, but since the
                    // interprter knows how to handle bitwise stuff and its in the number handling
                    // method, group it under numbers
                    tStatementType = 1;
                    break;
                case 5, 6: // Comparison and logical operators
                    tStatementType = 0;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid group: " + group);
            }
        }

        /**
         * Constructor for no return value.
         */
        public LeastImportantOperator() {
            this.op = null;
            this.index = -1;
            this.tStatementType = -1;
        }

        @Override
        public String toString() {
            return "LeastImportantOperator{" +
                    "op='" + op + '\'' +
                    ", index=" + index +
                    ", tStatementType=" + tStatementType +
                    '}';
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((op == null) ? 0 : op.hashCode());
            result = prime * result + index;
            result = prime * result + tStatementType;
            return result;
        }

        /**
         * Indicates whether some other object is "equal to" this one.
         * <p>
         * The method checks for reference equality, nullity, class type, and then
         * compares
         * the fields {@code op}, {@code index}, and {@code tStatementType} for
         * equality.
         * </p>
         *
         * @param obj the reference object with which to compare
         * @return {@code true} if this object is the same as the obj argument;
         *         {@code false} otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LeastImportantOperator other = (LeastImportantOperator) obj;
            if (op == null) {
                if (other.op != null)
                    return false;
            } else if (!op.equals(other.op))
                return false;
            if (index != other.index)
                return false;
            return tStatementType == other.tStatementType;
        }
    }

    /**
     * Determines the least important operator in a given mathematical or logical
     * statement.
     * The method analyzes the statement to find operators outside of parentheses or
     * brackets,
     * categorizes them by precedence, and identifies the least important operator
     * based on
     * its type and position.
     * <p>
     * This is for the TStatement class to use.
     *
     * @param statement The input string representing a mathematical or logical
     *                  expression.
     *                  It may contain operators, parentheses, and brackets.
     * @return A {@code LeastImportantOperator} object containing the operator, its
     *         position,
     *         and its precedence group. If no operator is found, an empty
     *         {@code LeastImportantOperator} object is returned.<br>
     *
     *         The method works as follows:<br>
     *         1. Iterates through the statement to identify operators outside of
     *         parentheses or brackets.<br>
     *         2. Categorizes operators by precedence groups (e.g., exponentiation,
     *         multiplication/division,
     *         addition/subtraction, boolean operators).<br>
     *         3. Identifies the least important operator based on its precedence
     *         group and position.<br>
     *         4. Handles multi-character operators (e.g., "&&", "||", ">=", "<=",
     *         "==").<br>
     *         <br>
     *
     *         Note: The method assumes the existence of helper classes and methods
     *         such as<br>
     *         {@code Validate.isOperator(char)}, {@code Operators.getAll()}, and
     *         {@code Operators.getType(String)}.
     */
    public static LeastImportantOperator leastImportantOperator(String statement) {
        int level = 0;
        if (statement.trim().isEmpty())
            return new LeastImportantOperator();
        ArrayList<Integer> indexes1 = new ArrayList<>();
        for (int i = 0; i < statement.length(); i++) {
            char c = statement.charAt(i);
            if ((c == '(' || c == '[') && Validate.isOpInQuotePair(statement, i) == -1)
                level++;
            else if ((c == ')' || c == ']') && Validate.isOpInQuotePair(statement, i) == -1)
                level--;
            else if (level == 0 && Validate.isOperator(c) && (Validate.isOpInQuotePair(statement, i) == -1))
                indexes1.add(i);

        }
//        indexes1 = sanitizeStatement(statement, indexes1);

        if (indexes1.isEmpty())
            return new LeastImportantOperator(); // exit early if no operators are found.
        int group = -1; // 0 = Exponentiation, 1 = DivMult, 2 = AddSub, 3 = Bitwise, 4 = Comparison, 5 =
        // Logical

        ArrayList<Pair<String, Integer>> indexes2 = new ArrayList<>(); // WHere the string, is the op itself, the
        // integer is the index.

        List<Character> multiOpChars = Arrays.asList('|', '&', '=', 'x', '>', '<'); // If the op is 2 chars long, it's
        // last
        // char must be one of these.
        List<Character> prevOpChars = Arrays.asList('|', '&', '!', '>', '<');
        for (int i = Chars.Operators.getAllLists().size() - 1; i >= 0; i--) {
            List<String> list = Chars.Operators.getAllLists().get(i);
            char first = 0;
            for (int opIndex : indexes1) {
                String op = statement.substring(opIndex, opIndex + 1);
                boolean isMulti = first != 0 || (opIndex + 1 != statement.length() && multiOpChars.contains(statement.charAt(opIndex + 1)));
                char prevChar = opIndex > 0 ? statement.charAt(opIndex - 1) : 0;
                op = statement.substring(
                                opIndex,
                                isMulti ? (opIndex + 2)
                                        : (opIndex + 1))
                        .trim();

                // String test = (first + op).trim();

                if (isMulti && first == 0)
                    first = op.charAt(0);
                else if (isMulti && prevChar != 0 && prevOpChars.contains(prevChar)
                    /* && Operators.getType(test) < i */) {
                    first = 0;
                    continue; // skip this iteration as we got it earlier.
                } else {
                    // clean up first
                    first = 0;
                }

                if (!list.contains(op))
                    continue;

                if (group == -1) {
                    group = i;
                    indexes2.add(new Pair<>(op, opIndex));
                } else if (group == Chars.Operators.getType(op)) {
                    indexes2.add(new Pair<>(op, opIndex));
                }
            }
        }

        if (indexes2.isEmpty())
            return new LeastImportantOperator();

        Pair<String, Integer> fTuple2 = indexes2.getLast();

        return new LeastImportantOperator(
                fTuple2.first(), fTuple2.second(),
                group);
    }


    /**
     * Finds pairs of double quotation marks within a given line of text,
     * ignoring escaped quotation marks.
     *
     * @param line The string to search for quotation mark pairs.
     * @return An {@link ArrayList} of {@link SamePair} where each pair represents the start and end index of a quotation block.
     */
    public static ArrayList<SamePair<Integer>> quotationPairs(String line) {
        ArrayList<SamePair<Integer>> arr = new ArrayList<>();
        int oldCharIndex = -1;

        for(int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            char before = i > 0 ? line.charAt(i - 1) : 0;
            char before2 = i > 1 ? line.charAt(i - 2) : 0;
            if (c == '"' && (before != '\\' || before2 == '\\')) {
                if (oldCharIndex == -1) {
                    oldCharIndex = i;
                } else {
                    arr.add(new SamePair<>(oldCharIndex, i));
                    oldCharIndex = -1;
                }
            }
        }

        return arr;
    }

    /**
     * Finds matching brace pairs (parentheses and square brackets) within a given line of text.
     * It also returns any unclosed braces.
     *
     * @param line The string to search for brace pairs.
     * @return A {@link BracePairs} instance containing a {@link Pair} with an {@link ArrayList} of {@link SamePair} for closed brace pairs (start and end index),
     *         and an {@link ArrayList} of {@link Pair} for unclosed braces (index and character).
     */
    public static BracePairs bracePairs(String line) {
        ArrayList<SamePair<Integer>> finalArr = new ArrayList<>();
        ArrayList<Pair<Integer, Character>> stack = new ArrayList<>();

        for(int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if ((c == '[' || c == '(') /*&& Validate.isOpInQuotePair(line, i) == -1*/) {
                stack.add(new Pair<>(i, c));
            }

            if ((c == ']' || c == ')') /*&& Validate.isOpInQuotePair(line, i) == -1*/) {
                Pair<Integer, Character> t = stack.getLast();
                if (t.second() == '[' && c == ']' || t.second() == '(' && c == ')') {
                    finalArr.add(new SamePair<>(t.first(), i));
                    stack.removeLast();
                }
            }
        }

        return new BracePairs(finalArr, stack);
    }

    /**
     * A record to hold the results of the {@link #bracePairs(String)} method.
     *
     * @param closedPairs An {@link ArrayList} of {@link SamePair} representing the start and end indices of closed brace pairs.
     * @param unclosedBraces An {@link ArrayList} of {@link Pair} where each pair contains the index and character of an unclosed brace.
     */
    public record BracePairs(
            ArrayList<SamePair<Integer>> closedPairs,
            ArrayList<Pair<Integer, Character>> unclosedBraces
    ) {}

    /**
     * Find the index of the enclosing character in a string.
     *
     * @param line  The string to search in.
     * @param start The starting character.
     * @param end   The ending character.
     * @return The index of the closing char. -1 if none found.
     */
    public static int closingCharIndex(String line, char start, char end) {
        int startCount = 0;
        int endCount = 0;
        boolean isStart = true;
        for (int i = 0; i < line.length(); i++) {
            if (start != end) {
                if (line.charAt(i) == start) {
                    startCount++;
                } else if (line.charAt(i) == end) {
                    endCount++;
                }
                if (startCount == endCount && (startCount != 0)) {
                    return i;
                }
            } else {
                if (line.charAt(i) == end) {
                    if (!isStart) {
                        return i;
                    }
                    isStart = false;
                }
            }
        }
        return -1;
    }

    /**
     * Method will return the index of the opening brace which is part of the last,
     * outermost brace pair. Both () and [] are checked for agasinst each other.
     * This will r
     * <p>
     * <blockquote>
     *
     * <pre>
     * "func()" returns 4
     * "()[]" returns 2
     * "()" returns 0
     * </pre>
     *
     * </blockquote>
     *
     * @param line Input.
     * @return the index where the outermost brace pair starts
     */
    public static int lastOutermostBracePair(String line) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int depth = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if ((c == '(' || c == '[') && Validate.isOpInQuotePair(line, i) == -1) {
                depth++;
                if (depth == 1)
                    indexes.add(i);
            }
            if ((c == ')' || c == ']') && Validate.isOpInQuotePair(line, i) == -1) {
                depth--;
            }
        }
        List<Integer> rIndexes = indexes.reversed();
        for (Integer i : rIndexes) {
            String sString = line.substring(i);
            char openingChar = line.charAt(i);
            int closingCharI = closingCharIndex(sString, openingChar, openingChar == '(' ? ')' : ']');
            if (
                    closingCharI == sString.length() - 1 || // Normal closing
                            closingCharI == sString.length() - 2 || // Account for length operator '~'
                            closingCharI == sString.length() - 4 // Account for spread operator ':::'
            )
                return i;
        }

        return -1;
    }

}
