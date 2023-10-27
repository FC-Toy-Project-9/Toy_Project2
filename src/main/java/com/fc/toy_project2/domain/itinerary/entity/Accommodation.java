package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationResponseDTO;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Accommodation extends Itinerary {

    private String accommodationName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String accommodationRoadAddressName;

    public ItineraryAccommodationResponseDTO toAccommodationResponseDTO(){
        return ItineraryAccommodationResponseDTO.builder()
                .accommodationName(this.accommodationName)
                .accommodationRoadAddressName(this.accommodationRoadAddressName)
                .checkIn(this.checkIn)
                .checkOut(this.checkOut)
                .build();
    }
}