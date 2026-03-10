package edu.touro.las.mcon364.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalWarmup {

    /**
     * Problem 1
     * Return a Supplier that gives the current month number (1-12).
     */
    public static Supplier<Integer> currentMonthSupplier() {
        Supplier<Integer> supplier = () -> LocalDate.now().getMonthValue();
        return supplier;
    }

    /**
     * Problem 2
     * Return a Predicate that is true only when the input string
     * has more than 5 characters.
     */
    public static Predicate<String> longerThanFive() {
        Predicate<String> predicate = (s) -> s.length() > 5;
        return predicate;
    }

    /**
     * Problem 3
     * Return a Predicate that checks whether a number is both:
     * - positive
     * - even
     * <p>
     * Prefer chaining smaller predicates.
     */
    public static Predicate<Integer> positiveAndEven() {
        Predicate<Integer> predicate = (s) -> s % 2 == 0 && s > 0;
        return predicate;
    }

    /**
     * Problem 4
     * Return a Function that counts words in a string.
     * <p>
     * Notes:
     * - Trim first.
     * - Blank strings should return 0.
     * - Words are separated by one or more spaces (use can use regex "\\s+")
     *
     */
    public static Function<String, Integer> wordCounter() {

        // Trim leading and trailing whitespaces
        Function<String, String> trimmer = (s) -> s.trim();
        Function<String, String[]> regex = (s) -> s.split("\\s+");
        // my logic is that the Function will take in one string at a time
        // from the regex String array and we will add 1 each time- to count the string.
        Function <String[], Integer> array_size = (s) -> +1;

        Function <String, Integer> pipeLine1 = trimmer.andThen(regex).andThen(array_size);
        return pipeLine1;

    }

    /**
     * Problem 5
     * Process the input labels as follows:
     * - remove blank strings
     * - trim whitespace
     * - convert to uppercase
     * - return the final list in the same relative order
     * <p>
     * Example:
     * ["  math ", "", " java", "  "] -> ["MATH", "JAVA"]
     */
    public static List<String> cleanLabels(List<String> labels) {

        // takes in a string and returns True if the String is empty
        Predicate<String> removeSpaces = (s) -> s.isEmpty();
        // Trim leading and trailing whitespace
        Function<String, String> trimmer = (s) -> s.trim();
        //The Function - receives input and returns --> will use .apply()
        Function<String, String> to_Upper_Case = s -> s.toUpperCase();

        List<String> new_labels = new ArrayList<>();
        for (String item : labels) {
            if (!removeSpaces.test(item)) {
                String transformed = trimmer.apply(item);
                String transformedUpperCase = to_Upper_Case.apply(transformed);
                new_labels.add(transformedUpperCase);
            }
        }
        return new_labels;
    }
}

