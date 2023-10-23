package com.fc.toy_project2.domain.itinerary.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Transportation extends Itinerary {

    private String transportation;

    private String departurePlace;

    private String destination;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;
}
