package com.pavbatol.talentium.app.exception;

public class SendingMailException extends AbstractBaseException {
    public static final String REASON = "Mail error.";

    public SendingMailException(String message, String reason) {
        super(message, reason);
    }

    public SendingMailException(String message) {
        super(message, REASON);
    }

}
