package com.pc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void add_anyNumbersParameter() {
        assertEquals("Should return 0", 0, stringCalculator.add(""));
    }
}
