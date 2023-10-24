package com.fc.toy_project2.global.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateTypeFormatterUtilTest {

    DateTypeFormatterUtil dateTypeFormatterUtil = new DateTypeFormatterUtil();

    @Nested
    @DisplayName("dateTimeFormatter()는 ")
    class Context_dateTimeFormatter{
        @Test
        @DisplayName("유효한 일시데이터라면 LocalDateTime 형으로 변환할 수 있다.")
        void _willSuccess(){
            //given
            String validDateTimeString = "2023-10-24 14:30:00";
            LocalDateTime expectedDateTime = LocalDateTime.of(2023, 10, 24, 14, 30, 0);

            //when
            LocalDateTime result = DateTypeFormatterUtil.dateTimeFormatter(validDateTimeString);

            //then
            assertEquals(expectedDateTime, result);
        }

        @Test
        @DisplayName("유효하지 않은 일시데이터라면 LocalDateTime 형으로 변환할 수 없다.")
        void InvalidDateFormat_willFail(){
            //given
            String invalidDateTimeString = "2023/10/24 14:30:00";
            String outOfRangeDateTimeString = "2023-10-44 14:30:00";


            //then
            assertThrows(DateTimeParseException.class, ()-> {
                DateTypeFormatterUtil.dateTimeFormatter(invalidDateTimeString);
            });

            assertThrows(DateTimeParseException.class, ()->{
                DateTypeFormatterUtil.dateTimeFormatter(outOfRangeDateTimeString);
            });
        }
    }

    @Nested
    @DisplayName("dateFormatter()는 ")
    class Context_dateFormatter{
        @Test
        @DisplayName("유효한 날짜데이터라면 LocalDate 형으로 변환할 수 있다.")
        void _willSuccess(){
            //given
            String validDateString = "2023-10-24";
            LocalDate expectedDate = LocalDate.of(2023, 10, 24);

            //when
            LocalDate result = DateTypeFormatterUtil.dateFormatter(validDateString);

            //then
            assertEquals(expectedDate, result);
        }

        @Test
        @DisplayName("유효하지 않은 날짜데이터라면 LocalDate 형으로 변환할 수 없다.")
        void InvalidDateFormat_willFail() {
            //given
            String invalidDateString = "2023/10/24";
            String outOfRangeDateString = "2023-10-44";


            //then
            assertThrows(DateTimeParseException.class, ()-> {
                DateTypeFormatterUtil.dateFormatter(invalidDateString);
            });

            assertThrows(DateTimeParseException.class, ()->{
                DateTypeFormatterUtil.dateFormatter(outOfRangeDateString);
            });
        }
    }
}