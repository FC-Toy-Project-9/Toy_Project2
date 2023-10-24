package com.fc.toy_project2.domain.itinerary.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Accommodation extends Itinerary {

    private String accommodationName;

    private String accommodationRoadAddressName;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;


    public Accommodation(String accommodationName, String accommodationRoadAddressName, LocalDateTime checkIn, LocalDateTime checkOut){
        this.accommodationName = accommodationName;
        this.accommodationRoadAddressName = accommodationRoadAddressName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
