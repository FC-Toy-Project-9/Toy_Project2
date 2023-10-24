package com.fc.toy_project2.domain.itinerary.dto.response;

import com.fc.toy_project2.domain.trip.entity.Trip;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryGetResponseDTO {

    private Long id;
    private Trip trip;

}
