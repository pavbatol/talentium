package com.pavbatol.talentium.app.exception;

public class BadRequestException extends AbstractBaseException {
    public static final String REASON = "Unsuitable request.";

    public BadRequestException(String message, String reason) {
        super(message, reason);
    }

    public BadRequestException(String message) {
        super(message, REASON);
    }
}
