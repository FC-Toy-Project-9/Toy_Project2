package com.fc.toy_project2.domain.itinerary.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ItineraryVisitDTO {

    private String placeName;
    private String placeRoadAddressName;
    private LocalDateTime visitArrivalTime;
    private LocalDateTime visitDepartureTime;
}
