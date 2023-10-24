package com.fc.toy_project2.domain.itinerary.dto;

import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.trip.entity.Trip;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItineraryGetDeleteDTO {

    private Long id;
    private Trip trip;

    private String itineraryType;

    private String accommodationName;
    private String accommodationRoadAddressName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    private String transportation;
    private String departurePlace;
    private String departurePlaceRoadAddressName;
    private String destination;
    private String destinationRoadAddressName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private String placeName;
    private String placeRoadAddressName;
    private LocalDateTime visitArrivalTime;
    private LocalDateTime visitDepartureTime;

    public Itinerary toItineraryEntity(){
        switch (itineraryType){
            case "Accommodation":
                return Accommodation.builder()
                    .trip(trip)
                    .accommodationName(accommodationName)
                    .accommodationRoadAddressName(accommodationRoadAddressName)
                    .checkIn(checkIn)
                    .checkOut(checkOut)
                    .build();

            case "Transportation":
                return Transportation.builder()
                    .trip(trip)
                    .transportation(transportation)
                    .departurePlace(departurePlace)
                    .departurePlaceRoadAddressName(departurePlaceRoadAddressName)
                    .destination(destination)
                    .destinationRoadAddressName(destinationRoadAddressName)
                    .departureTime(departureTime)
                    .arrivalTime(arrivalTime)
                    .build();

            case "Visit":
                return Visit.builder()
                    .trip(trip)
                    .placeName(placeName)
                    .placeRoadAddressName(placeRoadAddressName)
                    .arrivalTime(visitArrivalTime)
                    .departureTime(visitDepartureTime)
                    .build();

        }
        return null;
    }

}
