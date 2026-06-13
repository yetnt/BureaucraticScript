package com.yetnt.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Chars {
    public static final String SEPARATOR = ":";
    public static final String COMMENT = "~";

    /**
     * The Operators class defines a set of arithmetic and boolean operators.
     */
    public static class Operators {
        /**
         * List of exponentiation operators. (Well only one.)
         */
        public static final List<String> Exponentiation = List.of("^");
        /**
         * List of division, multiplication, and modulus operators.
         */
        public static final List<String> DivMult = Arrays.asList("%", "/", "*");
        /**
         * List of addition and subtraction operators.
         */
        public static final List<String> AddSub = Arrays.asList("+", "-");
        /**
         * Gotta have them shifts ong
         */
        public static final List<String> Shift = Arrays.asList(">>", "<<");
        /**
         * Bitwise operations
         */
        public static final List<String> Bitwise = Arrays.asList("&", "|");

        /**
         * Comparison operators
         * <p>
         * The ! operator is listed here, because
         * {@link Find#leastImportantOperator(String)} works on a character by character
         * basis. Therefore we can't actually just get 2 length operators immediately,
         * it will later add the next part of the operator when needed. Therefore,
         * {@code !} only exists, such that {@code !=} can also exist. If you don't get
         * it. Your fault for trying to udnerstand this mess. But an exmaple would be
         * the {@link Chars.Operators#Bitwise} which contains the single operators
         * {@code &} and {@code |}. If they didn't exist, then
         * {@link Chars.Operators#Logical}'s Operators being the {@code &&} and
         * {@code ||} would break. Just like deleting {@code !} would break {@code !=}.
         * Only difference being that {@code !} is not a valid operator, while every
         * other case is.
         */
        public static final List<String> Comparison = Arrays.asList("=", "!=", ">=", "<=", "<", ">", "!");

        /**
         * Logical operators
         */
        public static final List<String> Logical = Arrays.asList("||", "&&");

        /**
         * List of all arithmetic operators.
         * <p>
         * This method aggregates elements from the following lists:
         * - Exponentiation
         * - DivMult
         * - AddSub
         *
         * @return A list containing all arithmetic operators from the above categories.
         */
        public static List<String> getArithmetic() {
            List<String> all = new ArrayList<>();
            all.addAll(Exponentiation);
            all.addAll(DivMult);
            all.addAll(AddSub);
            return all;
        }

        /**
         * List of all boolean (Catch all term for anything returning a boolean)
         * operators.
         * <p>
         * This method aggregates elements from the following lists:
         * - Comparison
         * - Logical
         *
         * @return A list containing all boolean operators from the above categories.
         */
        public static List<String> getBoolean() {
            List<String> all = new ArrayList<>();
            all.addAll(Comparison);
            all.addAll(Logical);
            return all;
        }

        public static List<String> getInteger() {
            List<String> all = new ArrayList<>();
            all.addAll(Shift);
            all.addAll(Bitwise);
            return all;
        }

        /**
         * Retrieves a combined list of all elements from various predefined categories.
         * <p>
         * This method aggregates elements from the following lists:
         * - Exponentiation
         * - DivMult
         * - AddSub
         * - Shifts
         * - Bitwise
         * - Comparison
         * - Logical
         *
         * @return A list containing all elements from the above categories.
         */
        public static List<String> getAll() {
            List<String> all = new ArrayList<>();
            all.addAll(Exponentiation);
            all.addAll(DivMult);
            all.addAll(AddSub);
            all.addAll(Shift);
            all.addAll(Bitwise);
            all.addAll(Comparison);
            all.addAll(Logical);
            return all;
        }

        /**
         * Retrieves a combined list of all elements from various predefined categories.
         * <p>
         * This method uses {@link Chars.Operators#getAll()} to get all the operators.
         *
         * @return A list containing all the operators as characters
         */
        public static List<Character> getAllChars() {
            List<Character> all = new ArrayList<>();
            getAll().forEach(str -> all.add(str.charAt(0)));

            return new ArrayList<>(new HashSet<>(all));
        }

        /**
         * Retrieves a list of all predefined lists of strings.
         * <p>
         * This method aggregates several predefined lists, such as Exponentiation,
         * DivMult, AddSub, Shift, Bitwise, Comparison and Logical, into a single list
         * of lists.
         *
         * @return A list containing all predefined lists of strings.
         */
        public static List<List<String>> getAllLists() {
            List<List<String>> all = new ArrayList<>();
            all.add(Exponentiation);
            all.add(DivMult);
            all.add(AddSub);
            all.add(Shift);
            all.add(Bitwise);
            all.add(Comparison);
            all.add(Logical);
            return all;
        }

        /**
         * Determines the type of operator based on the provided string.
         * <p>
         * This follows your basic rules of Maths where exponentiation is the highest
         * priority and add/sub is lowest (With boolean operators being even lower.)
         *
         * @param op The operator string to check.
         * @return An integer representing the type of operator:
         *         0 - Exponentiation
         *         1 - Division/Multiplication
         *         2 - Addition/Subtraction
         *         3 - Shifts
         *         4 - Bitwise
         *         5 - Comparison
         *         6 - Logical
         *         -1 - Not an operator
         */
        public static int getType(String op) {
            if (Exponentiation.contains(op))
                return 0;
            else if (DivMult.contains(op))
                return 1;
            else if (AddSub.contains(op))
                return 2;
            else if (Shift.contains(op))
                return 3;
            else if (Bitwise.contains(op))
                return 4;
            else if (Comparison.contains(op))
                return 5;
            else if (Logical.contains(op))
                return 6;
            else
                return -1;
        }
    }
}
