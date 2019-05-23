package com.pc;

import com.pc.exceptions.NegativeNumberException;
import com.pc.exceptions.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        assertEquals("Should return " + expected, expected, stringCalculator.add(null));
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
    public void add_argumentIsSingleNumberWithLeadingZeros_returnThisNumber() {
        var expected = 1;
        assertEquals("Should return " + expected, expected, stringCalculator.add("00001"));
    }

    @Test
    public void add_argumentIsSingleDelimiter_returnZero() {
        var expected = 0;
        assertEquals("Should return " + expected, expected, stringCalculator.add(","));
    }

    @Test
    public void add_argumentHasTwoNumbers_returnProperSum() {
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
    public void add_argumentHasUnknownAmountOfNumbers_returnProperSum() {
        var expected = 31;
        assertEquals("Should return " + expected, expected, stringCalculator.add("2,3,7,6,4,9"));
    }

    @Test
    public void add_argumentContainsBigNumbers_returnProperSum() {
        var expected = 1049;
        assertEquals("Should return " + expected, expected, stringCalculator.add("12,340,72,1,624"));
    }

    @Test
    public void add_argumentContainsNewLineDelimiter_returnProperSum() {
        var expected = 5;
        assertEquals("Should return " + expected, expected, stringCalculator.add("2\n3"));
    }

    @Test
    public void add_argumentContainsMixedDelimiters_returnProperSum() {
        var expected = 12;
        assertEquals("Should return " + expected, expected, stringCalculator.add("2\n3,2\n3,2"));
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsNewLineAfterDelimiter_throwException() {
        try {
            stringCalculator.add("2\n3,2\n3,2,\n");
        } catch (ParseException e) {
            assertEquals('\n', e.getInvalidCharacter());
            assertEquals(11, e.getPoint());
            throw e;
        }
    }

    @Test
    public void add_argumentContainsHeaderSectionWithSingleDelimiter_returnProperSum() {
        var expected = 12;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[;]\n2;3;2;3;2"));
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[;]\n2;3;2\n3;2"));
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsHeaderSectionButNotAtTheBeginning_throwException() {
        try {
            stringCalculator.add("2//[;]\n123;123");
        } catch (ParseException e) {
            assertEquals('/', e.getInvalidCharacter());
            assertEquals(2, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsOnlyEmptyHeaderSection_throwException() {
        try {
            stringCalculator.add("//");
        } catch (ParseException e) {
            assertEquals('/', e.getInvalidCharacter());
            assertEquals(1, e.getPoint());
            throw e;
        }
    }

    @Test
    public void add_argumentContainsEmptyNumberSection_returnZero() {
        var expected = 0;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[;]\n"));
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsOnlyEmptyDelimiterDefinitionInHeaderSection_throwExceptionWhenEncountersDelimiter() {
        var expected = 123;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[]\n123"));
        try {
            stringCalculator.add("//[]\n123,123");
        } catch (ParseException e) {
            assertEquals(',', e.getInvalidCharacter());
            assertEquals(9, e.getPoint());
            throw e;
        }
    }

    @Test
    public void add_argumentContainsNumbersBiggerThan1000_ignoreBiggerThan1000AndReturnProperSum() {
        var expected = 1369;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[;][,]\n123,123,1234,1001,1000,123,99999"));
    }

    @Test
    public void add_argumentContainsEmptyDelimiterDefinitionInHeaderSection_returnProperSum() {
        var expected = 246;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[][;]\n123;123"));
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsIncompleteDelimiterDefinition_throwException() {
        try {
            stringCalculator.add("//[;\n123;123");
        } catch (ParseException e) {
            assertEquals('3', e.getInvalidCharacter());
            assertEquals(12, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsBrokenStartOfDelimiterDefinition_throwException() {
        try {
            stringCalculator.add("//;]\n123;123");
        } catch (ParseException e) {
            assertEquals(';', e.getInvalidCharacter());
            assertEquals(3, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsSecondIncompleteDelimiterDefinition_throwException() {
        try {
            stringCalculator.add("//[;][**\n123;123");
        } catch (ParseException e) {
            assertEquals('3', e.getInvalidCharacter());
            assertEquals(16, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsSecondBrokenStartOfDelimiterDefinition_throwException() {
        try {
            stringCalculator.add("//[;]**]\n123;123");
        } catch (ParseException e) {
            assertEquals('*', e.getInvalidCharacter());
            assertEquals(6, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsDoubledHeaderSection_throwException() {
        try {
            stringCalculator.add("//[;]\n//[;]\n123;123");
        } catch (ParseException e) {
            assertEquals('/', e.getInvalidCharacter());
            assertEquals(7, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsEmptyHeaderSection_throwExceptionWhenEncountersDelimiter() {
        var expected = 123;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[]\n123"));
        try {
            stringCalculator.add("//\n123;123");
        } catch (ParseException e) {
            assertEquals(';', e.getInvalidCharacter());
            assertEquals(7, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsHeaderSectionWithoutNewLine_throwException() {
        try {
            stringCalculator.add("//[;][,]123;123");
        } catch (ParseException e) {
            assertEquals('1', e.getInvalidCharacter());
            assertEquals(9, e.getPoint());
            throw e;
        }
    }


    @Test(expected = ParseException.class)
    public void add_argumentContainsOnlyHeaderSectionWithoutNewLine_throwException() {
        try {
            stringCalculator.add("//[;][,]");
        } catch (ParseException e) {
            assertEquals(']', e.getInvalidCharacter());
            assertEquals(8, e.getPoint());
            throw e;
        }
    }

    @Test
    public void add_argumentContainsDelimiterDefinitionWithHeaderSectionStart_returnProperSum() {
        var expected = 246;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[//]\n123//123"));
    }

    @Test
    public void add_argumentContainsDelimiterDefinitionWithNumbers_returnProperSum() {
        var expected = 369;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[456][32]\n12345612332123"));
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsUnsupportedDelimiter_throwException() {
        try {
            stringCalculator.add("//[.][;]\n123*123");
        } catch (ParseException e) {
            assertEquals('*', e.getInvalidCharacter());
            assertEquals(13, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsTwoDelimitersNextToEachOther_throwException() {
        try {
            stringCalculator.add("//[.][;]\n123.;123");
        } catch (ParseException e) {
            assertEquals(';', e.getInvalidCharacter());
            assertEquals(14, e.getPoint());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsUnknownCharInHeaderSection_throwException() {
        try {
            stringCalculator.add("//[.];[;]\n123;123");
        } catch (ParseException e) {
            assertEquals(';', e.getInvalidCharacter());
            assertEquals(6, e.getPoint());
            throw e;
        }
    }


    @Test
    public void add_argumentContainsLongDelimiter_returnProperSum() {
        var expected = 369;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[*****]\n123\n123*****123"));
    }

    @Test(expected = NegativeNumberException.class)
    public void add_argumentContainsNegativeNumbers_throwException() {
        List<Integer> invalidNumbers = new ArrayList<>();
        invalidNumbers.add(-56);
        invalidNumbers.add(-94);
        invalidNumbers.add(-14);
        invalidNumbers.add(-90);
        try {
            stringCalculator.add("2,45,-56,-94,12,-14,11,-90");
        } catch (NegativeNumberException e) {
            assertEquals(invalidNumbers, e.getInvalidNumbers());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void add_argumentContainsMinusInTheMiddleOfTheNumber_throwException() {
        try {
            stringCalculator.add("2,45,123,123-123");
        } catch (ParseException e) {
            assertEquals('-', e.getInvalidCharacter());
            assertEquals(13, e.getPoint());
            throw e;
        }
    }

    @Test
    public void add_argumentContainsMinusAsDelimiter_returnProperSum() {
        var expected = 322;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[-][,]\n2,43-56-94,12-14,11-90"));
    }

    @Test
    public void add_happyFlowExample() {
        var expected = 105;
        assertEquals("Should return " + expected, expected, stringCalculator.add("//[,][;][***][=====][?]\n1,2,3;4;5;6;7;8\n9?10?11***12\n13=====14,100000000"));
    }
}
