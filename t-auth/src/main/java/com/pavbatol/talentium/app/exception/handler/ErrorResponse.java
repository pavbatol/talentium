package com.pavbatol.talentium.app.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
public class ErrorResponse {
    LocalDateTime timestamp = LocalDateTime.now();

    String mapping;

    String status;

    String reason;

    String message;

    @JsonInclude(NON_NULL)
    String details;

    @JsonInclude(NON_NULL)
    List<String> errors;

    @JsonInclude(NON_NULL)
    List<String> trace;
}
