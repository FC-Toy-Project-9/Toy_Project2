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
public class Transportation extends Itinerary {

    private String transportation;

    private String departurePlace;

    private String departurePlaceRoadAddressName;

    private String destination;

    private String destinationRoadAddressName;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    public Transportation(String transportation, String departurePlace,
        String departurePlaceRoadAddressName, String destination,
        String destinationRoadAddressName, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.transportation = transportation;
        this.departurePlace = departurePlace;
        this.departurePlaceRoadAddressName = departurePlaceRoadAddressName;
        this.destination = destination;
        this.destinationRoadAddressName = destinationRoadAddressName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
