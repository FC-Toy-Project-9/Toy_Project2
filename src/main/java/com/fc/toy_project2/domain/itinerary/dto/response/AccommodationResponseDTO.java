package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccommodationResponseDTO {

    private String accommodationName;
    private String accommodationRoadAddressName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public static AccommodationResponseDTO of(String accommodationName,
                                              String accommodationRoadAddressName, LocalDateTime checkIn, LocalDateTime checkOut) {
        return AccommodationResponseDTO.builder()
                .accommodationName(accommodationName)
                .accommodationRoadAddressName(accommodationRoadAddressName)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }

}