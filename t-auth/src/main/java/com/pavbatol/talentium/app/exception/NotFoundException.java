package com.pavbatol.talentium.app.exception;

public class NotFoundException extends AbstractBaseException {
    public static final String REASON = "The required object was not found.";

    public NotFoundException(String message, String reason) {
        super(message, reason);
    }

    public NotFoundException(String message) {
        this(message, REASON);
    }
}
