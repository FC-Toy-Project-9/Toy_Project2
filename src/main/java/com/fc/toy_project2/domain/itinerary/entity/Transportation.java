package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.global.util.DateTypeFormatterUtil;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Transportation extends Itinerary{

    private String transportation;

    private String departurePlace;

    private String departurePlaceRoadAddressName;

    private String destination;

    private String destinationRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    @Builder
    public Transportation(Long id, Trip trip, String itineraryName, String transportation, String departurePlace,
        String departurePlaceRoadAddressName, String destination, String destinationRoadAddressName,
        LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.id = id;
        this.trip = trip;
        this.itineraryName = itineraryName;
        this.transportation = transportation;
        this.departurePlace = departurePlace;
        this.departurePlaceRoadAddressName = departurePlaceRoadAddressName;
        this.destination = destination;
        this.destinationRoadAddressName = destinationRoadAddressName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public TransportationResponseDTO toTransportationResponseDTO() {
        return TransportationResponseDTO.builder().itineraryId(super.getId())
            .itineraryName(super.getItineraryName()).transportation(this.transportation)
            .departurePlace(this.departurePlace)
            .departurePlaceRoadAddressName(this.departurePlaceRoadAddressName)
            .destination(this.destination)
            .destinationRoadAddressName(this.destinationRoadAddressName)
            .departureTime(DateTypeFormatterUtil.localDateTimeToString(this.departureTime))
            .arrivalTime(DateTypeFormatterUtil.localDateTimeToString(this.arrivalTime)).build();
    }

    public void updateTransportationInfo(String itineraryName, String transportation,
        String departurePlace, String departurePlaceRoadAddressName, String destination,
        String destinationRoadAddressName, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.itineraryName = itineraryName;
        this.transportation = transportation;
        this.departurePlace = departurePlace;
        this.departurePlaceRoadAddressName = departurePlaceRoadAddressName;
        this.destination = destination;
        this.destinationRoadAddressName = destinationRoadAddressName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
