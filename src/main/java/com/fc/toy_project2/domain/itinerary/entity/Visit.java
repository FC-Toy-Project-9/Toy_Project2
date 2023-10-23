package com.fc.toy_project2.domain.itinerary.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Visit extends Itinerary {

    private String placeName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;
}
