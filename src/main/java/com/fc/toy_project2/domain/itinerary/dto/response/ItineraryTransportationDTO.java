package com.fc.toy_project2.domain.itinerary.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ItineraryTransportationDTO {

    private String transportation;
    private String departurePlace;
    private String departurePlaceRoadAddressName;
    private String destination;
    private String destinationRoadAddressName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public static ItineraryTransportationDTO of(String transportation,
        String departurePlace,
        String departurePlaceRoadAddressName, String destination,
        String destinationRoadAddressName, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        return ItineraryTransportationDTO.builder()

            .transportation(transportation)
            .departurePlace(departurePlace)
            .departurePlaceRoadAddressName(departurePlaceRoadAddressName)
            .destination(destination)
            .destinationRoadAddressName(destinationRoadAddressName)
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build();
    }

}
