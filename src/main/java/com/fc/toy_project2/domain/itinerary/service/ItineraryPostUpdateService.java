package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.exception.InvalidAccommodationException;
import com.fc.toy_project2.domain.itinerary.exception.InvalidTransportationException;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.service.TripService;
import com.fc.toy_project2.global.util.DateTypeFormatterUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Transactional
@Service
@RequiredArgsConstructor
public class ItineraryPostUpdateService {

    private final ItineraryRepository itineraryRepository;
    private final TripService tripService;

    /**
     * 숙박에 관한 여정 생성
     *
     * @param itineraryAccommodationCreateDTO 숙박 여정 생성 요청 DTO
     * @param tripId                          여정을 추가할 여행의 ID
     * @return 생성된 숙박 여정 응답 DTO
     */
    public AccommodationResponseDTO Accommodation(
            ItineraryAccommodationCreateDTO itineraryAccommodationCreateDTO, Long tripId, Long itineraryId) {

        Trip trip = tripService.getTrip(tripId);
        LocalDateTime checkIn = DateTypeFormatterUtil.dateTimeFormatter(itineraryAccommodationCreateDTO.getCheckIn());
        LocalDateTime checkOut = DateTypeFormatterUtil.dateTimeFormatter(itineraryAccommodationCreateDTO.getCheckOut());
        checkAccommodationDate(trip, checkIn, checkOut);

        Itinerary existingItinerary = itineraryRepository.findByTripIdAndId(tripId, itineraryId);
        // 새로운 Itinerary 객체를 생성하고 필드 설정
        Itinerary updatedItinerary = Itinerary.builder()
                .trip(trip)
                .type(0) // 숙박 여정 유형
                .accommodationName(itineraryAccommodationCreateDTO.getAccommodationName())
                .accommodationRoadAddressName(itineraryAccommodationCreateDTO.getAccommodationRoadAddressName())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();

        if (existingItinerary == null) {
            // 이미 해당 여정이 데이터베이스에 존재하지 않는 경우, 새로운 여정을 저장합니다.
            existingItinerary = itineraryRepository.save(updatedItinerary);
        } else {
            // 이미 해당 여정이 데이터베이스에 존재하는 경우, 값을 업데이트합니다.
            existingItinerary.updateAccommodationInfo(
                    updatedItinerary.getAccommodationName(),
                    updatedItinerary.getAccommodationRoadAddressName(),
                    updatedItinerary.getCheckIn(),
                    updatedItinerary.getCheckOut()
            );
            existingItinerary = itineraryRepository.save(existingItinerary);
        }
        return existingItinerary.toAccommodationResponseDTO();
    }

    /**
     * 이동에 관한 여정 생성
     *
     * @param itineraryTransportationCreateDTO 이동에 관한 여정 생성 요청 DTO
     * @param tripId                           여정을 추가할 여행의 ID
     * @return 생성된 이동에 관한 여정 응답 DTO
     */
    public TransportationResponseDTO Transportation(
            ItineraryTransportationCreateDTO itineraryTransportationCreateDTO, Long tripId, Long itineraryId) {

        Trip trip = tripService.getTrip(tripId);
        LocalDateTime departureTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryTransportationCreateDTO.getDepartureTime());
        LocalDateTime arrivalTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryTransportationCreateDTO.getArrivalTime());

        checkTransportationVisitDate(trip, departureTime, arrivalTime);

        Itinerary existingItinerary = itineraryRepository.findByTripIdAndId(tripId, itineraryId);
        // 새로운 Itinerary 객체를 생성하고 필드 설정
        Itinerary updatedItinerary = Itinerary.builder()
                .trip(trip)
                .type(1) // 이동 여정 유형
                .transportation(itineraryTransportationCreateDTO.getTransportation())
                .departurePlace(itineraryTransportationCreateDTO.getDeparturePlace())
                .departurePlaceRoadAddressName(itineraryTransportationCreateDTO.getDeparturePlaceRoadAddressName())
                .destination(itineraryTransportationCreateDTO.getDestination())
                .destinationRoadAddressName(itineraryTransportationCreateDTO.getDestinationRoadAddressName())
                .arrivalTime(arrivalTime)
                .departureTime(departureTime)
                .build();

        if (existingItinerary == null) {
            // 이미 해당 여정이 데이터베이스에 존재하지 않는 경우, 새로운 여정을 저장합니다.
            existingItinerary = itineraryRepository.save(updatedItinerary);
        } else {
            // 이미 해당 여정이 데이터베이스에 존재하는 경우, 값을 업데이트합니다.
            existingItinerary.updateTransportationInfo(
                    updatedItinerary.getTransportation(),
                    updatedItinerary.getDeparturePlace(),
                    updatedItinerary.getDeparturePlaceRoadAddressName(),
                    updatedItinerary.getDestination(),
                    updatedItinerary.getDestinationRoadAddressName(),
                    updatedItinerary.getDepartureTime(),
                    updatedItinerary.getArrivalTime()
            );
            existingItinerary = itineraryRepository.save(existingItinerary);
        }
        return existingItinerary.toTransportationResponseDTO();
    }


    /**
     * 체류에 관한 여정 생성
     *
     * @param itineraryVisitCreateDTO 체류에 관한 여정 생성 요청 DTO
     * @param tripId                  여정을 추가할 여행의 ID
     * @return 생성된 체류에 관한 여정 응답 DTO
     */
    public VisitResponseDTO Visit(
            ItineraryVisitCreateDTO itineraryVisitCreateDTO, Long tripId, Long itineraryId) {

        Trip trip = tripService.getTrip(tripId);
        LocalDateTime visitDepartureTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryVisitCreateDTO.getDepartureTime());
        LocalDateTime visitArrivalTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryVisitCreateDTO.getArrivalTime());

        checkTransportationVisitDate(trip, visitDepartureTime, visitArrivalTime);

        Itinerary existingItinerary = itineraryRepository.findByTripIdAndId(tripId, itineraryId);
        // 새로운 Itinerary 객체를 생성하고 필드 설정
        Itinerary updatedItinerary = Itinerary.builder()
                .trip(trip)
                .type(1) // 이동 여정 유형
                .placeName(itineraryVisitCreateDTO.getPlaceName())
                .placeRoadAddressName(itineraryVisitCreateDTO.getPlaceRoadAddressName())
                .visitArrivalTime(visitArrivalTime)
                .visitDepartureTime(visitDepartureTime)
                .build();

        if (existingItinerary == null) {
            // 이미 해당 여정이 데이터베이스에 존재하지 않는 경우, 새로운 여정을 저장합니다.
            existingItinerary = itineraryRepository.save(updatedItinerary);
        } else {
            // 이미 해당 여정이 데이터베이스에 존재하는 경우, 값을 업데이트합니다.
            existingItinerary.updateVisitInfo(
                    updatedItinerary.getPlaceName(),
                    updatedItinerary.getPlaceRoadAddressName(),
                    updatedItinerary.getDepartureTime(),
                    updatedItinerary.getVisitArrivalTime()
            );
            existingItinerary = itineraryRepository.save(existingItinerary);
        }
        return existingItinerary.toVisitResponseDTO();
    }

    /**
     * 체류 및 이동에 관한 날짜 유효성 검사
     *
     * @param trip          여정이 속한 여행
     * @param departureTime 출발 시간
     * @param arrivalTime   도착 시간
     * @throws InvalidTransportationException 날짜 유효성 검사 실패 시 발생
     */
    private void checkTransportationVisitDate(Trip trip, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        LocalDateTime tripStartDateTime = trip.getStartDate().atStartOfDay();
        LocalDateTime tripEndDateTime = trip.getEndDate().atTime(LocalTime.MAX);

        if (departureTime.isAfter(arrivalTime)) {
            throw new InvalidTransportationException("출발 시간은 도착 시간보다 이른 시간이어야 합니다.");
        }
        if (departureTime.isAfter(tripEndDateTime)) {
            throw new InvalidTransportationException("출발 시간은 여행 종료일보다 빠른 시간이어야 합니다.");
        }
        if (arrivalTime.isBefore(tripStartDateTime)) {
            throw new InvalidTransportationException("도착 시간은 여행 시작일보다 빠른 시간이어야 합니다.");
        }
    }

    /**
     * 숙박 여정의 날짜 유효성 검사
     *
     * @param trip     여정이 속한 여행
     * @param checkIn  숙박 시작일
     * @param checkOut 숙박 종료일
     * @throws InvalidAccommodationException 날짜 유효성 검사 실패 시 발생
     */
    private void checkAccommodationDate(Trip trip, LocalDateTime checkIn, LocalDateTime checkOut) {
        LocalDateTime tripStartDateTime = trip.getStartDate().atStartOfDay();
        LocalDateTime tripEndDateTime = trip.getEndDate().atTime(LocalTime.MAX);

        if (checkIn.isAfter(checkOut)) {
            throw new InvalidAccommodationException("체크인 시간은 체크아웃 시간보다 이른 시간이어야 합니다.");
        }
        if (checkIn.isAfter(tripEndDateTime)) {
            throw new InvalidAccommodationException("체크인 시간은 여행 종료일보다 빠른 시간이어야 합니다.");
        }
        if (checkOut.isBefore(tripStartDateTime)) {
            throw new InvalidAccommodationException("체크아웃 시간은 여헹 시작일보다 빠른 시간이어야 합니다.");
        }
    }
}