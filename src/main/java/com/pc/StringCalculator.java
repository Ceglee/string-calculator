package com.pc;

public class StringCalculator {

    public int add(String numbers) {
        var sum = 0;
        if (numbers != null && !numbers.isEmpty()) {
            var partialNumbers = numbers.split(",");
            if (partialNumbers.length == 2) {
                sum = Integer.parseInt(partialNumbers[0]) + Integer.parseInt(partialNumbers[1]);
            } else {
                sum = Integer.parseInt(partialNumbers[0]);
            }
        }
        return sum;
    }
}
