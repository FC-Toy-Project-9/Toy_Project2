package com.fc.toy_project2.global.util;

import com.fc.toy_project2.global.exception.InvalidDateFormatException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class dateTypeFormatterUtil {

    /**
     * String을 LocalDateTime으로 변환해주는 메서드
     * @param dateTimeString 입력받은 String 일시데이터
     * @return LocalDateTime 일시데이터
     */
    public static LocalDateTime dateTimeFormatter(String dateTimeString){
        try {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateTimeString, formatter);
        }catch (DateTimeParseException e){
            e.printStackTrace();
            throw new InvalidDateFormatException();
        }
    }

    /**
     * String을 LocalDate로 변환해주는 메서드
     * @param dateString 입력받은 String 일시데이터
     * @return LocalDate 일시데이터
     */
    public static LocalDate dateFormatter(String dateString){
        try {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(dateString, formatter);
        }catch (DateTimeParseException e){
            e.printStackTrace();
            throw new InvalidDateFormatException();
        }
    }
}
