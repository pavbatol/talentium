package com.pavbatol.talentium.app.exception;

public class NotEnoughRightsException extends AbstractBaseException {
    public static final String REASON = "Insufficient access rights.";

    public NotEnoughRightsException(String message, String reason) {
        super(message, reason);
    }

    public NotEnoughRightsException(String message) {
        super(message, REASON);
    }

}
