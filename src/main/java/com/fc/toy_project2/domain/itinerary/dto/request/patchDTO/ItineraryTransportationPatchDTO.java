package com.fc.toy_project2.domain.itinerary.dto.request.patchDTO;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItineraryTransportationPatchDTO {

    private String transportation;
    private String departurePlace;
    private String departurePlaceRoadAddressName;
    private String destination;
    private String destinationRoadAddressName;
    private String departureTime;
    private String arrivalTime;
}