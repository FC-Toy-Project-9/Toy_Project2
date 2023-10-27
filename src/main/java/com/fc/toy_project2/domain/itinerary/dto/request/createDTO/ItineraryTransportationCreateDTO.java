package com.fc.toy_project2.domain.itinerary.dto.request.createDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryTransportationCreateDTO {
    @NotNull
    private String transportation;

    @NotNull
    private String departurePlace;

    @NotNull
    private String departurePlaceRoadAddressName;

    @NotNull
    private String destinationRoadAddressName;

    @NotNull
    private String destination;

    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;
}
