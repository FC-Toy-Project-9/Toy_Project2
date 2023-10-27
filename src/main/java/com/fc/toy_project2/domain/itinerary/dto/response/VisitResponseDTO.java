package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitResponseDTO {
    private String placeName;
    private String placeRoadAddressName;
    private String arrivalTime;
    private String departureTime;


//    public static VisitResponseDTO of(String placeName, String placeRoadAddressName,
//                                      LocalDateTime arrivalTime, LocalDateTime departureTime) {
//        return VisitResponseDTO.builder()
//                .placeName(placeName)
//                .arrivalTime(arrivalTime)
//                .departureTime(departureTime)
//                .placeRoadAddressName(placeRoadAddressName)
//                .build();
//    }
}