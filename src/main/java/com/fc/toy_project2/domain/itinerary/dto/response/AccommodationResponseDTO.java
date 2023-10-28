package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationResponseDTO {

    private Long itineraryId;
    private String itineraryName;
    private String accommodationName;
    private String accommodationRoadAddressName;
    private String checkIn;
    private String checkOut;

    public static AccommodationResponseDTO of(String accommodationName,
                                              String accommodationRoadAddressName, String checkIn, String checkOut) {
        return AccommodationResponseDTO.builder()
                .accommodationName(accommodationName)
                .accommodationRoadAddressName(accommodationRoadAddressName)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }
}