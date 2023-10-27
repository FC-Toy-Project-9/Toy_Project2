package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.patchDTO.ItineraryAccommodationPatchDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.patchDTO.ItineraryTransportationPatchDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.patchDTO.ItineraryVisitPatchDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.itinerary.exception.InvalidAccommodationException;
import com.fc.toy_project2.domain.itinerary.exception.InvalidTransportationException;
import com.fc.toy_project2.domain.itinerary.repository.AccommodationRepository;
import com.fc.toy_project2.domain.itinerary.repository.TransportationRepository;
import com.fc.toy_project2.domain.itinerary.repository.VisitRepository;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.service.TripService;
import com.fc.toy_project2.global.util.DateTypeFormatterUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Transactional
@Service
@RequiredArgsConstructor
public class ItineraryPostUpdateService {

    private final AccommodationRepository accommodationRepository;
    private final TransportationRepository transportationRepository;
    private final VisitRepository visitRepository;
    private final TripService tripService;

    /**
     * 숙박에 관한 여정 생성
     *
     * @param itineraryAccommodationCreateDTO 숙박 여정 생성 요청 DTO
     * @param tripId                          여정을 추가할 여행의 ID
     * @return 생성된 숙박 여정 응답 DTO
     */
    public ItineraryAccommodationResponseDTO createAccommodation(
            ItineraryAccommodationCreateDTO itineraryAccommodationCreateDTO, Long tripId) {

        LocalDateTime checkIn = DateTypeFormatterUtil.dateTimeFormatter(itineraryAccommodationCreateDTO.getCheckIn());
        LocalDateTime checkOut = DateTypeFormatterUtil.dateTimeFormatter(itineraryAccommodationCreateDTO.getCheckOut());

        Trip trip = tripService.getTrip(tripId);
        Accommodation accommodation = Accommodation.builder()
                .trip(trip)
                .accommodationName(itineraryAccommodationCreateDTO.getAccommodationName())
                .accommodationRoadAddressName(itineraryAccommodationCreateDTO.getAccommodationRoadAddressName())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
        checkAccommodationDate(trip, checkIn, checkOut);
        return accommodationRepository.save(accommodation).toAccommodationResponseDTO();
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

    /**
     * 이동에 관한 여정 생성
     *
     * @param itineraryTransportationCreateDTO 이동에 관한 여정 생성 요청 DTO
     * @param tripId                           여정을 추가할 여행의 ID
     * @return 생성된 이동에 관한 여정 응답 DTO
     */
    public ItineraryTransportationResponseDTO createTransportation(
            ItineraryTransportationCreateDTO itineraryTransportationCreateDTO, Long tripId) {
        Trip trip = tripService.getTrip(tripId);

        LocalDateTime departureTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryTransportationCreateDTO.getDepartureTime());
        LocalDateTime arrivalTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryTransportationCreateDTO.getArrivalTime());

        Transportation transportation = Transportation.builder()
                .trip(trip)
                .transportation(itineraryTransportationCreateDTO.getTransportation())
                .departurePlace(itineraryTransportationCreateDTO.getDeparturePlace())
                .departurePlaceRoadAddressName(itineraryTransportationCreateDTO.getDeparturePlaceRoadAddressName())
                .destination(itineraryTransportationCreateDTO.getDestination())
                .destinationRoadAddressName(itineraryTransportationCreateDTO.getDestinationRoadAddressName())
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .build();
        checkTransportationVisitDate(trip, departureTime, arrivalTime);
        return transportationRepository.save(transportation).toTransportationResponseDTO();
    }


    /**
     * 체류에 관한 여정 생성
     *
     * @param itineraryVisitCreateDTO 체류에 관한 여정 생성 요청 DTO
     * @param tripId                  여정을 추가할 여행의 ID
     * @return 생성된 체류에 관한 여정 응답 DTO
     */
    public ItineraryVisitResponseDTO createVisit(
            ItineraryVisitCreateDTO itineraryVisitCreateDTO, Long tripId) {
        Trip trip = tripService.getTrip(tripId);

        LocalDateTime departureTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryVisitCreateDTO.getDepartureTime());
        LocalDateTime arrivalTime = DateTypeFormatterUtil.dateTimeFormatter(itineraryVisitCreateDTO.getArrivalTime());

        Visit visit = Visit.builder()
                .trip(trip)
                .placeName(itineraryVisitCreateDTO.getPlaceName())
                .placeRoadAddressName(itineraryVisitCreateDTO.getPlaceRoadAddressName())
                .arrivalTime(arrivalTime)
                .departureTime(departureTime)
                .build();
        checkTransportationVisitDate(trip, departureTime, arrivalTime);
        return visitRepository.save(visit).toVisitResponseDTO();
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
     * 숙박 여정 정보를 업데이트하고 날짜 유효성을 검사합니다.
     *
     * @param patchDTO    업데이트할 숙박 여정 정보가 포함된 DTO
     * @param tripId      여정이 속한 여행의 ID
     * @param itineraryId 숙박 여정의 ID
     * @return 업데이트된 숙박 여정 정보
     * @throws EntityNotFoundException        숙박 여정을 찾을 수 없는 경우
     * @throws InvalidAccommodationException 숙박 여정이 주어진 여행에 속해 있지 않거나 날짜 유효성 검사 실패 시 발생
     */
    public ItineraryAccommodationResponseDTO patchAccommodation(
            ItineraryAccommodationPatchDTO patchDTO, Long tripId, Long itineraryId) {

        Accommodation existingAccommodation = (Accommodation) accommodationRepository.findById(itineraryId)
                .orElseThrow(() -> new EntityNotFoundException("숙박 여정을 찾을 수 없습니다."));

        Trip trip = tripService.getTrip(tripId);
        if (!existingAccommodation.getTrip().equals(trip)) {
            throw new InvalidAccommodationException("숙박 여정이 주어진 여행에 속해 있지 않습니다.");
        }

        // ItineraryAccommodationPatchDTO에서 엔티티로 값 복사
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(patchDTO, existingAccommodation);

        checkAccommodationDate(trip, existingAccommodation.getCheckIn(), existingAccommodation.getCheckOut());
        Accommodation updatedAccommodation = accommodationRepository.save(existingAccommodation);

        return updatedAccommodation.toAccommodationResponseDTO();
    }

    /**
     * 이동 여정 정보를 업데이트하고 날짜 유효성을 검사합니다.
     *
     * @param patchDTO    업데이트할 이동 여정 정보가 포함된 DTO
     * @param tripId      여정이 속한 여행의 ID
     * @param itineraryId 이동 여정의 ID
     * @return 업데이트된 이동 여정 정보
     * @throws EntityNotFoundException           이동 여정을 찾을 수 없는 경우
     * @throws InvalidTransportationException    이동 여정이 주어진 여행에 속해 있지 않거나 날짜 유효성 검사 실패 시 발생
     */
    public ItineraryTransportationResponseDTO patchTransportation(
            ItineraryTransportationPatchDTO patchDTO, Long tripId, Long itineraryId) {

        Transportation existingTransportation = (Transportation) transportationRepository.findById(itineraryId)
                .orElseThrow(() -> new EntityNotFoundException("숙박 여정을 찾을 수 없습니다."));

        Trip trip = tripService.getTrip(tripId);
        if (!existingTransportation.getTrip().equals(trip)) {
            throw new InvalidTransportationException("숙박 여정이 주어진 여행에 속해 있지 않습니다.");
        }

        // ItineraryAccommodationPatchDTO에서 엔티티로 값 복사
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(patchDTO, existingTransportation);

        checkTransportationVisitDate(trip, existingTransportation.getDepartureTime(), existingTransportation.getArrivalTime());
        Transportation updatedTransportation = accommodationRepository.save(existingTransportation);

        return updatedTransportation.toTransportationResponseDTO();
    }

    /**
     * 체류 여정 정보를 업데이트하고 날짜 유효성을 검사합니다.
     *
     * @param patchDTO    업데이트할 체류 여정 정보가 포함된 DTO
     * @param tripId      여정이 속한 여행의 ID
     * @param itineraryId 체류 여정의 ID
     * @return 업데이트된 체류 여정 정보
     * @throws EntityNotFoundException        체류 여정을 찾을 수 없는 경우
     * @throws InvalidAccommodationException 체류 여정이 주어진 여행에 속해 있지 않거나 날짜 유효성 검사 실패 시 발생
     */
    public ItineraryVisitResponseDTO patchVisit(
            ItineraryVisitPatchDTO patchDTO, Long tripId, Long itineraryId) {

        Visit existingVisit = (Visit) visitRepository.findById(itineraryId)
                .orElseThrow(() -> new EntityNotFoundException("숙박 여정을 찾을 수 없습니다."));

        Trip trip = tripService.getTrip(tripId);
        if (!existingVisit.getTrip().equals(trip)) {
            throw new InvalidAccommodationException("숙박 여정이 주어진 여행에 속해 있지 않습니다.");
        }

        // ItineraryAccommodationPatchDTO에서 엔티티로 값 복사
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(patchDTO, existingVisit);

        checkTransportationVisitDate(trip, existingVisit.getDepartureTime(), existingVisit.getArrivalTime());
        Visit updatedVisit = visitRepository.save(existingVisit);

        return updatedVisit.toVisitResponseDTO();
    }
}
