package com.fc.toy_project2.domain.itinerary.dto.response;

import com.fc.toy_project2.domain.trip.entity.Trip;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ItineraryTransportationDTO {

    private Trip trip;

    private String transportation;
    private String departurePlace;
    private String departurePlaceRoadAddressName;
    private String destination;
    private String destinationRoadAddressName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public static ItineraryTransportationDTO of(Trip trip, String transportation,
        String departurePlace,
        String departurePlaceRoadAddressName, String destination,
        String destinationRoadAddressName, LocalDateTime departureTime, LocalDateTime arrivalTime){
        return ItineraryTransportationDTO.builder()
            .trip(trip)
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
