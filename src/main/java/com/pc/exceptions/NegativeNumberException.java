package com.pc.exceptions;

import java.util.List;

public class NegativeNumberException extends RuntimeException {

    private List<Integer> invalidNumbers;

    public NegativeNumberException(List<Integer> invalidNumbers) {
        super("Negative number/s found in parsed collection");
        this.invalidNumbers = invalidNumbers;
    }

    public List<Integer> getInvalidNumbers() {
        return invalidNumbers;
    }
}
