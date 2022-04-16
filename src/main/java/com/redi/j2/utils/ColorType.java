package com.redi.j2.utils;

/**
 * List of supported 3-bit and 4-bit ANSI color escaping characters.
 * Reference List: https://en.wikipedia.org/wiki/ANSI_escape_code#3-bit_and_4-bit
 */
public enum ColorType {

    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    GRAY("\u001B[90m"),
    BRIGHT_RED("\u001B[91m"),
    BRIGHT_GREEN("\u001B[92m"),
    BRIGHT_YELLOW("\u001B[93m"),
    BRIGHT_BLUE("\u001B[94m"),
    BRIGHT_PURPLE("\u001B[95m"),
    BRIGHT_CYAN("\u001B[96m"),
    BRIGHT_WHITE("\u001B[97m");

    private final String ansiCode;

    ColorType(String ansiCode) {
        this.ansiCode = ansiCode;
    }
    
    public String getAnsiCode() {
        return ansiCode;
    }
}
