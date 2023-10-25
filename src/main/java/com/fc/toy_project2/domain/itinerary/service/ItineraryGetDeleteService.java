package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryGetResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitDTO;
import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.service.TripService;
import jakarta.persistence.DiscriminatorValue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItineraryGetDeleteService {

    private final TripService tripService;
    private final ItineraryRepository itineraryRepository;

    public List getItineraryByTripId(Long tripId) {
        Trip trip = tripService.getTrip(tripId);
        List<Itinerary> itineraryList = trip.getItineraries();
        List<Object> itineraryResponseList = new ArrayList<>();

        for (Itinerary itinerary : itineraryList) {
            String dtype = itinerary.getClass().getAnnotation(DiscriminatorValue.class).value();
            switch (dtype) {
                case "Accommodation":
                    Accommodation accommodation = ((Accommodation) itinerary);
                    String accommodationName = accommodation.getAccommodationName();
                    String accommodationRoadAddressName = accommodation.getAccommodationRoadAddressName();
                    LocalDateTime checkIn = accommodation.getCheckIn();
                    LocalDateTime checkOut = accommodation.getCheckOut();
                    itineraryResponseList.add(ItineraryAccommodationDTO.of(
                        accommodationName, accommodationRoadAddressName, checkIn,
                        checkOut));

                case "Transportation":
                    Transportation transportation = ((Transportation) itinerary);
                    String transportationName = transportation.getTransportation();
                    String departurePlace = transportation.getDeparturePlace();
                    String departurePlaceRoadAddressName = transportation.getDeparturePlaceRoadAddressName();
                    String destination = transportation.getDestination();
                    String destinationRoadAddressName = transportation.getDestinationRoadAddressName();
                    LocalDateTime departureTime = transportation.getDepartureTime();
                    LocalDateTime arrivalTime = transportation.getArrivalTime();
                    itineraryResponseList.add(ItineraryTransportationDTO.of(
                        transportationName, departurePlace, departurePlaceRoadAddressName,
                        destination, destinationRoadAddressName, departureTime, arrivalTime));

                case "Visit":
                    Visit visit = ((Visit) itinerary);
                    String placeName = visit.getPlaceName();
                    String placeRoadAddressName = visit.getPlaceRoadAddressName();
                    LocalDateTime visitArrivalTime = visit.getArrivalTime();
                    LocalDateTime visitDepartureTime = visit.getDepartureTime();
                    itineraryResponseList.add(ItineraryVisitDTO.of(placeName,
                        placeRoadAddressName, visitArrivalTime, visitDepartureTime));

            }
        }
        return itineraryResponseList;
    }

    public ItineraryGetResponseDTO deleteItinerary(Long itineraryId) throws NotFoundException {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow(
            NotFoundException::new);
        itineraryRepository.delete(itinerary);
        ItineraryGetResponseDTO itineraryGetResponseDTO = ItineraryGetResponseDTO.of(
            itinerary.getId(), itinerary.getTrip());

        return itineraryGetResponseDTO;

    }


}
