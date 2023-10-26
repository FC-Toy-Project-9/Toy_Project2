package com.fc.toy_project2.domain.itinerary.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryAccommodationDTO {

    private String accommodationName;
    private String accommodationRoadAddressName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
