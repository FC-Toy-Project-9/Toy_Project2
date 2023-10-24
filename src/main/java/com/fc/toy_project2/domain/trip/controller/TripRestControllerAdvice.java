package com.fc.toy_project2.domain.trip.controller;

import com.fc.toy_project2.domain.trip.exception.TripNotFoundException;
import com.fc.toy_project2.domain.trip.exception.WrongTripEndDateException;
import com.fc.toy_project2.domain.trip.exception.WrongTripStartDateException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Trip REST Controller Advice
 */
@RestControllerAdvice
public class TripRestControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> tripNotFoundException(TripNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", e.getMessage());
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> wrongTripStartDateException(
        WrongTripStartDateException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", e.getMessage());
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> wrongTripEndDateException(
        WrongTripEndDateException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", e.getMessage());
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
