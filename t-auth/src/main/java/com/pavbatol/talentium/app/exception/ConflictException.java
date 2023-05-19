package com.pavbatol.talentium.app.exception;

public class ConflictException extends AbstractBaseException {
    public static final String REASON = "Conflict occurred.";

    public ConflictException(String message, String reason) {
        super(message, reason);
    }

    public ConflictException(String message) {
        super(message, REASON);
    }

}
