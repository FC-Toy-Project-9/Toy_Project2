package com.fc.toy_project2.domain.itinerary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryVisitCreateDTO {
    @NotNull
    private String placeName;
    @NotNull
    private String placeRoadAddressName;
    @NotBlank
    private String arrivalTime;
    @NotBlank
    private String departureTime;

}
