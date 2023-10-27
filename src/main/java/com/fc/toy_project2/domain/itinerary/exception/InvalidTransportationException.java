package com.fc.toy_project2.domain.itinerary.exception;

/**
 * 여정의 이동 정보가 유효하지 않을 때 발생하는 예외
 */
public class InvalidTransportationException extends RuntimeException {
    public InvalidTransportationException(String message) {
        super(message);
    }
}