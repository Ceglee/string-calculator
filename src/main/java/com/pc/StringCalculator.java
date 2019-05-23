package com.pc;

import com.pc.exceptions.NegativeNumberException;
import com.pc.exceptions.ParseException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    public int add(String numbers) {
        if (numbers != null && !numbers.isEmpty()) {
            var result = new Parser().parse(numbers);

            List<Integer> negativeNumbers = result.stream()
                    .map(Integer::parseInt)
                    .filter(number -> number < 0)
                    .collect(Collectors.toList());

            if (negativeNumbers.size() != 0) {
                throw new NegativeNumberException(negativeNumbers);
            }

            return result.stream()
                    .mapToInt(Integer::parseInt)
                    .filter(val -> val <= 1000)
                    .sum();
        }
        return 0;
    }

    private class Parser {
        private static final char COMMA_DELIMITER = ',';
        private static final char NEW_LINE_DELIMITER = '\n';
        private static final char START_HEADER_SECTION = '/';
        private static final char START_DELIMITER_DEFINITION = '[';
        private static final char END_DELIMITER_DEFINITION = ']';
        private static final char NUMERIC_SPECIAL = '-';

        private final char[] NUMERIC_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        private final List<char[]> DEFAULT_DELIMITERS = List.of(new char[]{COMMA_DELIMITER}, new char[]{NEW_LINE_DELIMITER});

        private String sentence;
        private char[] elements;
        private int pointer = 0;
        private List<char[]> delimiters;
        private VisitedElement visited;

        public List<String> parse(String sentence) {
            reset(sentence);

            List<String> result = new LinkedList<>();
            var numberBuilder = new StringBuilder();

            while (exists()) {
                if (isDelimiter()) {
                    visited = VisitedElement.DELIMITER;
                    if (numberBuilder.length() != 0) {
                        result.add(numberBuilder.toString());
                        numberBuilder.setLength(0);
                    }
                } else if (isHeaderSection()) {
                    delimiters = new LinkedList<>();
                    delimiters.add(new char[]{NEW_LINE_DELIMITER});
                    handleHeaderSection();
                    pointer--;
                } else if (isNumber()) {
                    visited = VisitedElement.NUMBER;
                    numberBuilder.append(elements[pointer]);
                } else {
                    throw buildException();
                }
                pointer++;
            }
            if (numberBuilder.length() != 0) {
                result.add(numberBuilder.toString());
                numberBuilder.setLength(0);
            }
            return result;
        }

        private void reset(String sentence) {
            this.sentence = sentence;
            elements = sentence.toCharArray();
            pointer = 0;
            delimiters = DEFAULT_DELIMITERS;
            visited = VisitedElement.HEADER;
        }

        private boolean isDelimiter() {
            for (var delimiter : delimiters) {
                for (var i = 0; i < delimiter.length; i++) {
                    if (pointer + i == elements.length || elements[pointer + i] != delimiter[i]) {
                        break;
                    }
                    if (i + 1 == delimiter.length) {
                        pointer += i;
                        return visited != VisitedElement.DELIMITER;
                    }
                }
            }
            return false;
        }

        private boolean isNumber() {
            if (elements[pointer] == NUMERIC_SPECIAL && visited != VisitedElement.NUMBER) {
                return true;
            }

            for (var number : NUMERIC_CHARS) {
                if (elements[pointer] == number) {
                    return true;
                }
            }
            return false;
        }

        private boolean isHeaderSection() {
            if (visited == VisitedElement.HEADER && elements[pointer] == START_HEADER_SECTION && elements.length > 2) {
                if (elements[pointer + 1] == START_HEADER_SECTION) {
                    pointer += 2;
                    return true;
                }
                throw buildException();
            }
            return false;
        }

        private void handleHeaderSection() {
            if (elements[pointer] == NEW_LINE_DELIMITER) {
                return;
            } else if (elements[pointer] == START_DELIMITER_DEFINITION) {
                pointer++;
                handleDelimiterDefinition();
                handleHeaderSection();
            } else {
                throw buildException();
            }
        }

        private void handleDelimiterDefinition() {
            var builder = new StringBuilder();
            while (exists()) {
                if (elements[pointer] == END_DELIMITER_DEFINITION) {
                    delimiters.add(builder.toString().toCharArray());
                    pointer++;
                    return;
                }
                builder.append(elements[pointer++]);
            }
            throw buildException();
        }

        private boolean exists() {
            return this.pointer < elements.length;
        }

        private ParseException buildException() {
            var invalidCharacter = exists() ? elements[pointer] : elements[pointer - 1];
            var pointer = exists() ? this.pointer + 1: this.pointer;
            var message = String.format("Invalid character %s at point %s in sentence: %s", invalidCharacter, pointer, sentence);
            return new ParseException(message, invalidCharacter, pointer);
        }
    }

    private enum VisitedElement {
        DELIMITER, NUMBER, HEADER
    }
}
