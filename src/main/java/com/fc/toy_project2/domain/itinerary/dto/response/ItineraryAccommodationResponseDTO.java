package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItineraryAccommodationResponseDTO {

    private String accommodationName;
    private String accommodationRoadAddressName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public static ItineraryAccommodationResponseDTO of(String accommodationName,
                                                       String accommodationRoadAddressName, LocalDateTime checkIn, LocalDateTime checkOut) {
        return ItineraryAccommodationResponseDTO.builder()
                .accommodationName(accommodationName)
                .accommodationRoadAddressName(accommodationRoadAddressName)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }

}