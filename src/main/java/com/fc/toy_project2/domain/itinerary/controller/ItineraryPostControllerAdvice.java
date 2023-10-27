package com.fc.toy_project2.domain.itinerary.controller;

//import com.fc.toy_project2.domain.itinerary.dto.response.GetResponseDTO;
import com.fc.toy_project2.domain.itinerary.exception.InvalidAccommodationException;
import com.fc.toy_project2.domain.itinerary.exception.InvalidTransportationException;
import com.fc.toy_project2.domain.itinerary.exception.InvalidVisitException;
import com.fc.toy_project2.global.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItineraryPostControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> invalidAccommodationException(
            InvalidAccommodationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.res(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> invalidTransportationException(
            InvalidTransportationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.res(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> invalidVisitException(
            InvalidVisitException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.res(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
