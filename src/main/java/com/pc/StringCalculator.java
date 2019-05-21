package com.pc;

import java.util.Arrays;

public class StringCalculator {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_DELIMITER = "\n";

    public int add(String numbers) {
        var sum = 0;
        if (numbers != null && !numbers.isEmpty()) {
            sum = Arrays.stream(numbers.split(COMMA_DELIMITER))
                    .flatMap(partialNumber -> Arrays.stream(partialNumber.split(NEW_LINE_DELIMITER)))
                    .filter(partialNumber -> !partialNumber.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .sum();
        }
        return sum;
    }
}
