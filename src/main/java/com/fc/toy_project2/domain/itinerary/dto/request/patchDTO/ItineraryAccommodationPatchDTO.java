package com.fc.toy_project2.domain.itinerary.dto.request.patchDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryAccommodationPatchDTO {

    private String accommodationName;
    private String accommodationRoadAddressName;
    private String checkIn;
    private String checkOut;
}