package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@SuperBuilder@Getter
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

    public TransportationResponseDTO toTransportationResponseDTO(){
        return TransportationResponseDTO.builder()
                .transportation(this.transportation)
                .departurePlace(this.departurePlace)
                .departurePlaceRoadAddressName(this.departurePlaceRoadAddressName)
                .destination(this.destination)
                .destinationRoadAddressName(this.destinationRoadAddressName)
                .departureTime(this.departureTime)
                .arrivalTime(this.arrivalTime)
                .build();
    }
}
