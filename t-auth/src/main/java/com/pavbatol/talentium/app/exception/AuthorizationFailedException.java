package com.pavbatol.talentium.app.exception;

public class AuthorizationFailedException extends AbstractBaseException {

    public static final String REASON = "Authentication failed";

    public AuthorizationFailedException(String message, String reason) {
        super(message, reason);
    }

    public AuthorizationFailedException(String message) {
        super(message, REASON);
    }
}
