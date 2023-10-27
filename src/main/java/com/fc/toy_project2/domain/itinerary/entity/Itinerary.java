package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.trip.entity.Trip;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Comment("0: Accommodation, 1: Transportation, 2: Visit")
    private int type;

    /** 숙박 **/
    private String accommodationName;

    private String accommodationRoadAddressName;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    /** 이동 **/
    private String transportation;

    private String departurePlace;

    private String departurePlaceRoadAddressName;

    private String destination;

    private String destinationRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;


    /** 체류 **/
    private String placeName;

    private String placeRoadAddressName;

    private LocalDateTime visitArrivalTime;

    private LocalDateTime visitDepartureTime;

    public AccommodationResponseDTO toAccommodationResponseDTO(){
    return AccommodationResponseDTO.builder()
            .accommodationName(this.accommodationName)
            .accommodationRoadAddressName(this.accommodationRoadAddressName)
            .checkIn(String.valueOf(this.checkIn))
            .checkOut(String.valueOf(this.checkOut))
            .build();
    }

    public TransportationResponseDTO toTransportationResponseDTO(){
    return TransportationResponseDTO.builder()
            .transportation(this.transportation)
            .departurePlace(this.departurePlace)
            .departurePlaceRoadAddressName(this.departurePlaceRoadAddressName)
            .destination(this.destination)
            .destinationRoadAddressName(this.destinationRoadAddressName)
            .departureTime(String.valueOf(this.departureTime))
            .arrivalTime(String.valueOf(this.arrivalTime))
            .build();
    }

    public VisitResponseDTO toVisitResponseDTO(){
    return VisitResponseDTO.builder()
            .placeName(this.placeName)
            .placeRoadAddressName(this.placeRoadAddressName)
            .departureTime(String.valueOf(this.departureTime))
            .arrivalTime(String.valueOf(this.arrivalTime))
            .build();
    }
    public void updateAccommodationInfo(
            String accommodationName,
            String accommodationRoadAddressName,
            LocalDateTime checkIn,
            LocalDateTime checkOut) {
        this.accommodationName = accommodationName;
        this.accommodationRoadAddressName = accommodationRoadAddressName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public void updateTransportationInfo(
            String transportation,
            String departurePlace,
            String departurePlaceRoadAddressName,
            String destination,
            String destinationRoadAddressName,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime) {
        this.transportation = transportation;
        this.departurePlace = departurePlace;
        this.departurePlaceRoadAddressName = departurePlaceRoadAddressName;
        this.destination = destination;
        this.destinationRoadAddressName = destinationRoadAddressName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public void updateVisitInfo(
            String placeName,
            String placeRoadAddressName,
            LocalDateTime visitDepartureTime,
            LocalDateTime visitArrivalTime) {
        this.placeName = placeName;
        this.placeRoadAddressName = placeRoadAddressName;
        this.visitDepartureTime = visitDepartureTime;
        this.visitArrivalTime = visitArrivalTime;
    }
}
