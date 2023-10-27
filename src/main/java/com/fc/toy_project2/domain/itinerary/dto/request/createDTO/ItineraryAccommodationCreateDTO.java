package com.fc.toy_project2.domain.itinerary.dto.request.createDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryAccommodationCreateDTO {

    @NotNull
    private String accommodationName;

    @NotNull
    private String accommodationRoadAddressName;

    @NotBlank
    private String checkIn;

    @NotBlank
    private String checkOut;
}