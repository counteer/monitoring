package com.aldisued.iot.monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SensorAlreadyExistsException.class)
    public Map<String, String> handleSensorAlreadyExists(SensorAlreadyExistsException e) {
        return Map.of("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AlertNotFoundException.class)
    public Map<String, String> handleAlertNotFound(AlertNotFoundException e) {
        return Map.of("message", e.getMessage());
    }
}
