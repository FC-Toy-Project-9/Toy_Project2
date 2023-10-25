package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.trip.entity.Trip;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Visit extends Itinerary {

    private String placeName;

    private String placeRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;


    public Visit(Long id, Trip trip, String placeName, String placeRoadAddressName,
        LocalDateTime arrivalTime,
        LocalDateTime departureTime) {
        super(id, trip);
        this.placeName = placeName;
        this.placeRoadAddressName = placeRoadAddressName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
