package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class VisitResponseDTO {

    private Long itineraryId;
    private String placeName;
    private String placeRoadAddressName;
    private String arrivalTime;
    private String departureTime;
}
