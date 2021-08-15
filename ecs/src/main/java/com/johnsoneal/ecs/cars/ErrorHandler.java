package com.johnsoneal.ecs.cars;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * General handler error and validation errors.
 */
@ControllerAdvice
public class ErrorHandler
{
    private static final Logger log = LoggerFactory
        .getLogger(ErrorHandler.class);

    /**
     * Handler any uncaught exceptions
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(
        Exception exception, WebRequest request)
    {
        String path = request.getDescription(false);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("type", exception.getClass().getSimpleName());
        body.put("path", path);

        log.error("{} error {}", path, body);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle the formatting of constraint violations from validation issues.
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
        ConstraintViolationException exception, WebRequest request)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = exception.getConstraintViolations().stream()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .collect(Collectors.toList());

        String path = request.getDescription(false);
        String message = status.getReasonPhrase();
        Map<String, Object> body = new LinkedHashMap<>();
        if (!errors.isEmpty())
        {
            body.put("timestamp", Instant.now());
            body.put("status", status.value());
            body.put("type", exception.getClass().getSimpleName());
            body.put("path", path);
            body.put("message", message);
            body.put("errors", errors);
            message = body.toString();
        }

        log.error("{} error {}", path, message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception, WebRequest request)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String path = request.getDescription(false);
        String message = status.getReasonPhrase();
        log.error("{} error {}", path, message);
        return new ResponseEntity<>(message, status);
    }

}
