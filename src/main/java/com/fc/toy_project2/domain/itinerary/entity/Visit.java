package com.fc.toy_project2.domain.itinerary.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Visit extends Itinerary {

    private String placeName;

    private String placeRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    @Builder
    public Visit(String placeName, String placeRoadAddressName, LocalDateTime arrivalTime,
        LocalDateTime departureTime){
        this.placeName = placeName;
        this.placeRoadAddressName = placeRoadAddressName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
