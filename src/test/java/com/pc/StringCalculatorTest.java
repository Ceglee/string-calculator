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
    public void add_argumentIsNull_returnZero() {
        var expected = 0;
        assertEquals("Should return " + expected, expected, stringCalculator.add(""));
    }

    @Test
    public void add_argumentIsEmpty_returnZero() {
        var expected = 0;
        assertEquals("Should return " + expected, expected, stringCalculator.add(""));
    }

    @Test
    public void add_argumentIsSingleNumber_returnThisNumber() {
        var expected = 1;
        assertEquals("Should return " + expected, expected, stringCalculator.add("1"));
    }

    @Test
    public void add_argumentHasTwoNumbers_returnSum() {
        var expected = 3;
        assertEquals("Should return " + expected, expected, stringCalculator.add("1,2"));
    }

    @Test
    public void add_argumentHasTwoNumbers_numberOrderDoesNotMatter() {
        var expected = 5;
        assertEquals("Should return " + expected, expected, stringCalculator.add("2,3"));
        assertEquals("Should return " + expected, expected, stringCalculator.add("3,2"));
    }

    @Test
    public void add_argumentHasMoreNumbers_returnSum() {
        var expected = 31;
        assertEquals("Should return " + expected, expected, stringCalculator.add("2,3,7,6,4,9"));
    }

    @Test
    public void add_argumentContainsBiggerNumbers_returnSum() {
        var expected = 178;
        assertEquals("Should return " + expected, expected, stringCalculator.add("12,30,72,64"));
    }

    @Test
    public void add_argumentContainsDoubledDelimiter_omitDoubledDelimiters() {
        var expected = 178;
        assertEquals("Should return " + expected, expected, stringCalculator.add("12,30,,,,72,64"));
        assertEquals("Should return " + expected, expected, stringCalculator.add("12,,,30,,72,,,64,,"));
    }
}
