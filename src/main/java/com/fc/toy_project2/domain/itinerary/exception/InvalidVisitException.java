package com.fc.toy_project2.domain.itinerary.exception;

/**
 * 여정의 체류 정보가 유효하지 않을 때 발생하는 예외
 */
public class InvalidVisitException extends RuntimeException {
    public InvalidVisitException(String message) {
        super(message);
    }
}
