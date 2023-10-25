package com.fc.toy_project2.domain.trip.service;

import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.response.TripResponseDTO;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.exception.TripNotFoundException;
import com.fc.toy_project2.domain.trip.exception.WrongTripEndDateException;
import com.fc.toy_project2.domain.trip.exception.WrongTripStartDateException;
import com.fc.toy_project2.domain.trip.repository.TripRepository;
import jakarta.persistence.DiscriminatorColumn;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Trip Service
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    /**
     * 여행 정보 목록 조회
     *
     * @return 여행 정보 응답 DTO 리스트
     */
    public List<TripResponseDTO> getTrips() {
        List<TripResponseDTO> trips = new ArrayList<>();
        List<Trip> tripList = tripRepository.findAll(Sort.by(Direction.ASC, "id"));
        for (Trip trip : tripList) {
            trips.add(trip.toTripResponseDTO());
        }
        return trips;
    }

    /**
     * 특정 ID 값에 해당하는 여행 정보 조회
     *
     * @param id 조회할 하는 여행 ID
     * @return 여행 정보 응답 DTO
     */
    public TripResponseDTO getTripById(Long id) {
        return getTrip(id).toTripResponseDTO();
    }

    public TripResponseDTO updateTrip(UpdateTripRequestDTO updateTripRequestDTO) {
        Trip trip = getTrip(updateTripRequestDTO.getId());
        // TODO String 파싱은 global util을 사용하도록 수정
        checkTripDate(trip, LocalDate.parse(updateTripRequestDTO.getStartDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalDate.parse(updateTripRequestDTO.getEndDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        trip.updateTrip(updateTripRequestDTO);
        return trip.toTripResponseDTO();
    }

    /**
     * 특정 ID 값에 해당하는 여행 정보 Entity 조회
     *
     * @param id 조회할 하는 여행 ID
     * @return 여행 정보 Entity
     */
    public Trip getTrip(Long id) {
        return tripRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }

    /**
     * 여행 일자 검증
     *
     * @param trip      여행 entity
     * @param startDate 여행 시작일
     * @param endDate   여행 종료일
     */
    private void checkTripDate(Trip trip, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(getMaxDate(trip.getItineraries())) || startDate.isAfter(endDate)) {
            throw new WrongTripStartDateException();
        }
        if (endDate.isBefore(getMinDate(trip.getItineraries()))) {
            throw new WrongTripEndDateException();
        }
    }

    /**
     * 알맞은 최대 여행 시작일 반환
     *
     * @param itineraries 여정 리스트
     * @return 알맞은 최소 여행 시작일
     */
    private LocalDate getMaxDate(List<Itinerary> itineraries) {
        LocalDate max = LocalDate.MAX;
        for (Itinerary itinerary : itineraries) {
            if (itinerary instanceof Transportation) {
                if (((Transportation) itinerary).getDepartureTime().toLocalDate().isBefore(max)) {
                    max = ((Transportation) itinerary).getDepartureTime().toLocalDate();
                }
            } else if (itinerary instanceof Accommodation) {
                if (((Accommodation) itinerary).getCheckIn().toLocalDate().isBefore(max)) {
                    max = ((Accommodation) itinerary).getCheckIn().toLocalDate();
                }
            } else {
                if (((Visit) itinerary).getDepartureTime().toLocalDate().isBefore(max)) {
                    max = ((Visit) itinerary).getDepartureTime().toLocalDate();
                }
            }
        }
        return max;
    }

    /**
     * 알맞은 최소 여행 종료일 반환
     *
     * @param itineraries 여정 리스트
     * @return 알맞은 최소 여행 시작일
     */
    private LocalDate getMinDate(List<Itinerary> itineraries) {
        LocalDate min = LocalDate.MIN;
        for (Itinerary itinerary : itineraries) {
            if (itinerary instanceof Transportation) {
                if (((Transportation) itinerary).getArrivalTime().toLocalDate().isAfter(min)) {
                    min = ((Transportation) itinerary).getArrivalTime().toLocalDate();
                }
            } else if (itinerary instanceof Accommodation) {
                if (((Accommodation) itinerary).getCheckOut().toLocalDate().isAfter(min)) {
                    min = ((Accommodation) itinerary).getCheckOut().toLocalDate();
                }
            } else {
                if (((Visit) itinerary).getArrivalTime().toLocalDate().isAfter(min)) {
                    min = ((Visit) itinerary).getArrivalTime().toLocalDate();
                }
            }
        }
        return min;
    }
}
