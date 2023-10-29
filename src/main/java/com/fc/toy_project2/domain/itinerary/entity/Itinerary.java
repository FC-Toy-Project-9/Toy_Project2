package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.global.util.DateTypeFormatterUtil;
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

    private String itineraryName;

    /**
     * 숙박
     **/
    private String accommodationName;

    private String accommodationRoadAddressName;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    /**
     * 이동
     **/
    private String transportation;

    private String departurePlace;

    private String departurePlaceRoadAddressName;

    private String destination;

    private String destinationRoadAddressName;

    /**
     * 체류
     **/
    private String placeName;

    private String placeRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    public AccommodationResponseDTO toAccommodationResponseDTO() {
        return AccommodationResponseDTO.builder().itineraryId(this.id)
            .itineraryName(this.itineraryName).accommodationName(this.accommodationName)
            .accommodationRoadAddressName(this.accommodationRoadAddressName)
            .checkIn(DateTypeFormatterUtil.localDateTimeToString(this.checkIn))
            .checkOut(DateTypeFormatterUtil.localDateTimeToString(this.checkOut)).build();
    }

    public TransportationResponseDTO toTransportationResponseDTO() {
        return TransportationResponseDTO.builder().itineraryId(this.id)
            .itineraryName(this.itineraryName).transportation(this.transportation)
            .departurePlace(this.departurePlace)
            .departurePlaceRoadAddressName(this.departurePlaceRoadAddressName)
            .destination(this.destination)
            .destinationRoadAddressName(this.destinationRoadAddressName)
            .departureTime(DateTypeFormatterUtil.localDateTimeToString(this.departureTime))
            .arrivalTime(DateTypeFormatterUtil.localDateTimeToString(this.arrivalTime)).build();
    }

    public VisitResponseDTO toVisitResponseDTO() {
        return VisitResponseDTO.builder().itineraryId(this.id).itineraryName(this.itineraryName)
            .placeName(this.placeName).placeRoadAddressName(this.placeRoadAddressName)
            .departureTime(DateTypeFormatterUtil.localDateTimeToString(this.departureTime))
            .arrivalTime(DateTypeFormatterUtil.localDateTimeToString(this.arrivalTime)).build();
    }

    public void updateAccommodationInfo(String itineraryName, String accommodationName,
        String accommodationRoadAddressName, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.itineraryName = itineraryName;
        this.accommodationName = accommodationName;
        this.accommodationRoadAddressName = accommodationRoadAddressName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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

    public void updateVisitInfo(String itineraryName, String placeName, String placeRoadAddressName,
        LocalDateTime visitDepartureTime, LocalDateTime visitArrivalTime) {
        this.itineraryName = itineraryName;
        this.placeName = placeName;
        this.placeRoadAddressName = placeRoadAddressName;
        this.departureTime = visitDepartureTime;
        this.arrivalTime = visitArrivalTime;
    }
}
