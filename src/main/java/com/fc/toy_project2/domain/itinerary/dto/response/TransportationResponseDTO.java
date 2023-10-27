package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class TransportationResponseDTO {

    private Long itineraryId;
    private String transportation;
    private String departurePlace;
    private String departurePlaceRoadAddressName;
    private String destination;
    private String destinationRoadAddressName;
    private String departureTime;
    private String arrivalTime;

//    public static TransportationResponseDTO of(String transportation,
//                                               String departurePlace,
//                                               String departurePlaceRoadAddressName, String destination,
//                                               String destinationRoadAddressName, LocalDateTime departureTime, LocalDateTime arrivalTime) {
//        return TransportationResponseDTO.builder()
//
//                .transportation(transportation)
//                .departurePlace(departurePlace)
//                .departurePlaceRoadAddressName(departurePlaceRoadAddressName)
//                .destination(destination)
//                .destinationRoadAddressName(destinationRoadAddressName)
//                .departureTime(departureTime)
//                .arrivalTime(arrivalTime)
//                .build();
//    }
}