package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class ItineraryVisitResponseDTO {
    private String placeName;
    private String placeRoadAddressName;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;


    public static ItineraryVisitResponseDTO of(String placeName, String placeRoadAddressName,
                                               LocalDateTime arrivalTime, LocalDateTime departureTime) {
        return ItineraryVisitResponseDTO.builder()
                .placeName(placeName)
                .arrivalTime(arrivalTime)
                .departureTime(departureTime)
                .placeRoadAddressName(placeRoadAddressName)
                .build();
    }
}