package com.fc.toy_project2.domain.itinerary.dto.request.patchDTO;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItineraryVisitPatchDTO {

    private String placeName;
    private String placeRoadAddressName;
    private String arrivalTime;
    private String departureTime;
}