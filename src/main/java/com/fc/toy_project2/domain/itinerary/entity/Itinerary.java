package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
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

    /** 체류 **/
    private String placeName;

    private String placeRoadAddressName;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    public AccommodationResponseDTO toAccommodationResponseDTO(){
    return AccommodationResponseDTO.builder()
            .accommodationName(this.accommodationName)
            .accommodationRoadAddressName(this.accommodationRoadAddressName)
            .checkIn(String.valueOf(this.checkIn))
            .checkOut(String.valueOf(this.checkOut))
            .build();
    }

}
//package com.fc.toy_project2.domain.itinerary.entity;
//
//import com.fc.toy_project2.domain.trip.entity.Trip;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//@SuperBuilder
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "DTYPE")
//public class Itinerary {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trip_id")
//    private Trip trip;
//
//    public Itinerary(Long id, Trip trip) {
//        this.id = id;
//        this.trip = trip;
//    }
//}