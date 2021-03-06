package com.pc.exceptions;

public class ParseException extends IllegalArgumentException {

    private final char invalidCharacter;
    private final int point;

    public ParseException(String s, char invalidCharacter, int point) {
        super(s);
        this.invalidCharacter = invalidCharacter;
        this.point = point;
    }

    public char getInvalidCharacter() {
        return invalidCharacter;
    }

    public int getPoint() {
        return point;
    }
}
