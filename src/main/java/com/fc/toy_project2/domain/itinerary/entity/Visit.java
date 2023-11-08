package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
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
public class Visit extends Itinerary {

    private String placeName;

    private String placeRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    @Builder
    public Visit(Long id, Trip trip, String itineraryName, String placeName,
        String placeRoadAddressName, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.id = id;
        this.trip = trip;
        this.itineraryName = itineraryName;
        this.placeName = placeName;
        this.placeRoadAddressName = placeRoadAddressName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public VisitResponseDTO toVisitResponseDTO() {
        return VisitResponseDTO.builder().itineraryId(super.getId())
            .itineraryName(super.getItineraryName()).placeName(this.placeName)
            .placeRoadAddressName(this.placeRoadAddressName)
            .departureTime(DateTypeFormatterUtil.localDateTimeToString(this.departureTime))
            .arrivalTime(DateTypeFormatterUtil.localDateTimeToString(this.arrivalTime)).build();
    }

    public void updateVisitInfo(String itineraryName, String placeName, String placeRoadAddressName,
        LocalDateTime visitDepartureTime, LocalDateTime visitArrivalTime) {
        this.itineraryName = itineraryName;
        this.placeName = placeName;
        this.placeRoadAddressName = placeRoadAddressName;
        this.departureTime = visitDepartureTime;
        this.arrivalTime = visitArrivalTime;
    }
}