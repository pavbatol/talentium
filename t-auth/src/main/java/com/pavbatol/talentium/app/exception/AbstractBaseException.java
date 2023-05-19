package com.pavbatol.talentium.app.exception;

import lombok.Getter;

public class AbstractBaseException extends RuntimeException{

    @Getter
    private final String reason;

    public AbstractBaseException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
