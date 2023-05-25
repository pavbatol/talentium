package com.pavbatol.talentium.app.exception;

public class ValidationException extends AbstractBaseException {
    public static final String REASON = "Validation failed.";

    public ValidationException(String message, String reason) {
        super(message, reason);
    }

    public ValidationException(String message) {
        super(message, REASON);
    }
}
