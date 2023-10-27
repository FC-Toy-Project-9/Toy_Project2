//package com.fc.toy_project2.domain.itinerary.controller;
//
//import com.fc.toy_project2.domain.itinerary.exception.ItineraryNotFoundException;
//import com.fc.toy_project2.global.DTO.ResponseDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ItineraryGetDeleteControllerAdvice {
//
//    @ExceptionHandler
//    public ResponseEntity<ResponseDTO> itineraryNotFoundException(ItineraryNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//            .body(ResponseDTO.res(HttpStatus.BAD_REQUEST, e.getMessage()));
//    }
//
//}
