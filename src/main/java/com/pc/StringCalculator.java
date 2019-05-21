package com.pc;

public class StringCalculator {

    public int add(String numbers) {
        var sum = 0;
        if (numbers != null && !numbers.isEmpty()) {
            var partialNumbers = numbers.split(",");
            for (var partialNumber : partialNumbers) {
                if (!partialNumber.isEmpty()) {
                    sum += Integer.parseInt(partialNumber);
                }
            }
        }
        return sum;
    }
}
