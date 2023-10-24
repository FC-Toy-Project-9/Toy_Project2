package com.fc.toy_project2.domain.itinerary.dto.response;

import com.fc.toy_project2.domain.trip.entity.Trip;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ItineraryVisitDTO {

    private Trip trip;

    private String placeName;
    private String placeRoadAddressName;
    private LocalDateTime visitArrivalTime;
    private LocalDateTime visitDepartureTime;

    public static ItineraryVisitDTO of(Trip trip, String placeName, String placeRoadAddressName,
        LocalDateTime arrivalTime,
        LocalDateTime departureTime){
        return ItineraryVisitDTO.builder()
            .trip(trip)
            .placeName(placeName)
            .placeRoadAddressName(placeRoadAddressName)
            .visitArrivalTime(arrivalTime)
            .visitDepartureTime(departureTime)
            .build();
    }

}
