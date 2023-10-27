package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationResponseDTO {

    private Long itineraryId;
    private String accommodationName;
    private String accommodationRoadAddressName;
    private String checkIn;
    private String checkOut;
}
