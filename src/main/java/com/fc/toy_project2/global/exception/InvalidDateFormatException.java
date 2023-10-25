package com.fc.toy_project2.global.exception;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException(){
        super("올바르지 않은 형식의 날짜입니다.");
    }
}
