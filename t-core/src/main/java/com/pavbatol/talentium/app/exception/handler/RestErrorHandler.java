package com.pavbatol.talentium.app.exception.handler;

import com.pavbatol.talentium.app.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class RestErrorHandler {

    public static final String UNEXPECTED_ERROR = "Unexpected error";
    public static final String NO_HANDLER_FOUND = "No handler found";
    public static final String NOT_READABLE_JSON = "Not readable JSON";
    public static final String INTEGRITY_CONSTRAINT_HAS_BEEN_VIOLATED = "Integrity constraint has been violated.";
    public static final String INCORRECTLY_MADE_REQUEST = "Incorrectly made request.";
    public static final String MISSING_SERVLET_REQUEST_PARAMETER = "Missing servlet request parameter";
    public static final String NOT_FOUND_OR_UNAVAILABLE = "Not found or unavailable";
    public static final String INVALID_ARGUMENT = "Invalid argument";

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValidEx(MethodArgumentNotValidException ex, WebRequest request) {
        return makeResponseEntity(INCORRECTLY_MADE_REQUEST, ex, BAD_REQUEST, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchEx(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return makeResponseEntity(INCORRECTLY_MADE_REQUEST, ex, BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        return makeResponseEntity(INTEGRITY_CONSTRAINT_HAS_BEEN_VIOLATED, ex, CONFLICT, request);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleHttpMessageNotReadableEx(HttpMessageNotReadableException ex, WebRequest request) {
        return makeResponseEntity(NOT_READABLE_JSON, ex, CONFLICT, request);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    protected ResponseEntity<Object> handleNoHandlerFoundEx(NoHandlerFoundException ex, WebRequest request) {
        return makeResponseEntity(NO_HANDLER_FOUND, ex, BAD_REQUEST, request);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<Object> handleMissingServletRequestParameterEx(MissingServletRequestParameterException ex, WebRequest request) {
        return makeResponseEntity(MISSING_SERVLET_REQUEST_PARAMETER, ex, BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleEmptyResultDataAccessEx(EmptyResultDataAccessException ex, WebRequest request) {
        return makeResponseEntity(NOT_FOUND_OR_UNAVAILABLE, ex, NOT_FOUND, request);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundEx(NotFoundException ex, WebRequest request) {
        return makeResponseEntity(ex.getReason(), ex, NOT_FOUND, request);
    }

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleNotFoundEx(ValidationException ex, WebRequest request) {
        return makeResponseEntity(ex.getReason(), ex, BAD_REQUEST, request);
    }

    @ExceptionHandler({ConflictException.class})
    protected ResponseEntity<Object> handleConflictEx(ConflictException ex, WebRequest request) {
        return makeResponseEntity(ex.getReason(), ex, CONFLICT, request);
    }

    @ExceptionHandler({AuthorizationFailedException.class})
    protected ResponseEntity<Object> handleConflictEx(AuthorizationFailedException ex, WebRequest request) {
        return makeResponseEntity(ex.getReason(), ex, UNAUTHORIZED, request);
    }

    @ExceptionHandler({NotEnoughRightsException.class})
    protected ResponseEntity<Object> handleNotEnoughRightsEx(NotEnoughRightsException ex, WebRequest request) {
        return makeResponseEntity(ex.getReason(), ex, FORBIDDEN, request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentEx(IllegalArgumentException ex, WebRequest request) {
        return makeResponseEntity(INVALID_ARGUMENT, ex, BAD_REQUEST, request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleThrowableEx(Throwable ex, WebRequest request) {
        return makeResponseEntity(UNEXPECTED_ERROR, ex, INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> makeResponseEntity(String reason, Throwable ex, HttpStatus status, WebRequest request) {
        log.error("{}: {}", reason, ex.getMessage());
        ex.printStackTrace();
        ErrorResponse errorResponse = makeBody(reason, status, request, ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ErrorResponse makeBody(String reason, HttpStatus status, WebRequest request, Throwable ex) {
        List<String> errors;
        if (ex instanceof BindException) {
            errors = ((BindException) ex)
                    .getAllErrors()
                    .stream()
                    .map(this::getErrorString)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else {
            errors = Arrays.stream(ex.getMessage().split(", ")).collect(Collectors.toList());
        }

        List<String> stackTrace = UNEXPECTED_ERROR.equals(reason)
                ? Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
                : null;
        String message = !errors.isEmpty() ? errors.get(0) : "No message";
        String details = !errors.isEmpty() && !Objects.equals(ex.getMessage(), errors.get(0)) ? ex.getMessage() : null;

        return new ErrorResponse(
                getRequestURI(request),
                status.name(),
                reason,
                message,
                details,
                errors.size() > 1 ? errors : null,
                stackTrace);
    }

    private String getErrorString(ObjectError error) {
        if (error instanceof FieldError) {
            FieldError fieldError = ((FieldError) error);
            String fieldName = fieldError.getField();
            String defMassage = fieldError.getDefaultMessage();
            String rejectedValue = fieldError.getRejectedValue() != null && !"".equals(fieldError.getRejectedValue())
                    ? fieldError.getRejectedValue().toString()
                    : "null";
            return String.format("Field: %s. Error: %s. Value: %s", fieldName, defMassage, rejectedValue);
        }
        return error.getDefaultMessage();
    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest requestHttp = ((ServletWebRequest) request).getRequest();
            return String.format("%s %s", requestHttp.getMethod(), requestHttp.getRequestURI());
        } else {
            return "";
        }
    }
}
