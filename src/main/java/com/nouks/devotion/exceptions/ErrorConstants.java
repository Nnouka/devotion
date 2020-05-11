package com.nouks.devotion.exceptions;

public final class ErrorConstants {
    public static final String INVALID_FORMAT_HINT = "Validate user input";
    public static final String UNKNOWN_CLIENT = "Client must be authenticated";
    private ErrorConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
