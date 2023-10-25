package com.fc.toy_project2.domain.itinerary.dto.response;

import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.trip.entity.Trip;
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

    public static ItineraryAccommodationDTO of(String accommodationName,
        String accommodationRoadAddressName, LocalDateTime checkIn, LocalDateTime checkOut){
        return ItineraryAccommodationDTO.builder()
            .accommodationName(accommodationName)
            .accommodationRoadAddressName(accommodationRoadAddressName)
            .checkIn(checkIn)
            .checkOut(checkOut)
            .build();
    }

}
