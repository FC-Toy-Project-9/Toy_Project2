package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitDTO;
import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.itinerary.repository.AccommodationRepository;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.itinerary.repository.TransportationRepository;
import com.fc.toy_project2.domain.itinerary.repository.VisitRepository;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.repository.TripRepository;
import com.fc.toy_project2.domain.trip.service.TripService;
import jakarta.persistence.DiscriminatorValue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItineraryGetDeleteService {

    private final TripRepository tripRepository;
    private final ItineraryRepository itineraryRepository;
    private final AccommodationRepository accommodationRepository;
    private final TransportationRepository transportationRepository;
    private final VisitRepository visitRepository;
    private final TripService tripService;
    private Optional<Trip> trip;

    public List getItineraryByTripId(Long tripId) {
        trip = tripRepository.findById(tripId);
        List<Itinerary> ItineraryList = new ArrayList<>();
        List ItineraryResponseList = new ArrayList<>();
        if (trip.isPresent()) {
            ItineraryList = trip.get().getItineraries();
        }
        for (Itinerary itinerary : ItineraryList) {
            String dtype = itinerary.getClass().getAnnotation(DiscriminatorValue.class).value();
            switch (dtype) {
                case "Accommodation":
                    Optional<Accommodation> accommodationById = accommodationRepository.findById(
                        itinerary.getId());
                    Trip tripAInfo = accommodationById.get().getTrip();
                    String accommodationName = accommodationById.get().getAccommodationName();
                    String accommodationRoadAddressName = accommodationById.get()
                        .getAccommodationRoadAddressName();
                    LocalDateTime checkIn = accommodationById.get().getCheckIn();
                    LocalDateTime checkOut = accommodationById.get().getCheckOut();
                    ItineraryAccommodationDTO itineraryAccommodationDTO = ItineraryAccommodationDTO.of(
                        tripAInfo, accommodationName, accommodationRoadAddressName, checkIn,
                        checkOut);
                    ItineraryResponseList.add(itineraryAccommodationDTO);

                case "Transportation":
                    Optional<Transportation> transportationById = transportationRepository.findById(
                        itinerary.getId());
                    Trip tripTInfo = transportationById.get().getTrip();
                    String transportation = transportationById.get().getTransportation();
                    String departurePlace = transportationById.get().getDeparturePlace();
                    String departurePlaceRoadAddressName = transportationById.get()
                        .getDeparturePlaceRoadAddressName();
                    String destination = transportationById.get().getDestination();
                    String destinationRoadAddressName = transportationById.get()
                        .getDestinationRoadAddressName();
                    LocalDateTime departureTime = transportationById.get().getDepartureTime();
                    LocalDateTime arrivalTime = transportationById.get().getArrivalTime();
                    ItineraryTransportationDTO itineraryTransportationDTO = ItineraryTransportationDTO.of(
                        tripTInfo, transportation, departurePlace, departurePlaceRoadAddressName,
                        destination, destinationRoadAddressName, departureTime, arrivalTime);
                    ItineraryResponseList.add(itineraryTransportationDTO);

                case "Visit":
                    Optional<Visit> visitById = visitRepository.findById(itinerary.getId());
                    Trip tripVInfo = visitById.get().getTrip();
                    String placeName = visitById.get().getPlaceName();
                    String placeRoadAddressName = visitById.get().getPlaceRoadAddressName();
                    LocalDateTime visitArrivalTime = visitById.get().getArrivalTime();
                    LocalDateTime visitDepartureTime = visitById.get().getDepartureTime();
                    ItineraryVisitDTO itineraryVisitDTO = ItineraryVisitDTO.of(tripVInfo, placeName,
                        placeRoadAddressName, visitArrivalTime, visitDepartureTime);
                    ItineraryResponseList.add(itineraryVisitDTO);

            }
        }
        return ItineraryResponseList;
    }


}
