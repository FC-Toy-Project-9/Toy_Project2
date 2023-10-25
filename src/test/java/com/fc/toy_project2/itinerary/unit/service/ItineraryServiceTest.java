package com.fc.toy_project2.itinerary.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryGetResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitDTO;
import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.repository.TripRepository;
import com.fc.toy_project2.domain.trip.service.TripService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
public class ItineraryServiceTest {

    @InjectMocks
    private ItineraryGetDeleteService itineraryGetDeleteService;


    @Mock
    private ItineraryRepository itineraryRepository;
    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripService tripService;

    @Nested
    @DisplayName("getItineraryByTripId()는")
    class Context_getItineraryByTripId {

        @Test
        @DisplayName("tripId를 통해 Itinerary를 조회할 수 있다.")
        void _willSuccess() throws NotFoundException {

            // given
            Itinerary accommodation = Accommodation.builder().accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn(LocalDateTime.of(2023, 10, 25, 15, 0))
                .checkOut(LocalDateTime.of(2023, 10, 26, 11, 0)).build();

            Itinerary transportation = Transportation.builder()
                .transportation("카카오택시").departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime(LocalDateTime.of(2023, 10, 26, 12, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 26, 13, 0)).build();

            Itinerary visit = Visit.builder().placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .arrivalTime(LocalDateTime.of(2023, 10, 26, 14, 0))
                .departureTime(LocalDateTime.of(2023, 10, 26, 16, 0)).build();

            List<Itinerary> itineraryList = new ArrayList<>();
            itineraryList.add(accommodation);
            itineraryList.add(transportation);
            itineraryList.add(visit);

            Trip trip = Trip.builder().id(1L).name("제주도 여행").startDate(LocalDate.of(2023, 10, 23))
                .endDate(LocalDate.of(2023, 10, 27)).isDomestic(true)
                .itineraries(itineraryList).build();
            given(tripService.getTrip(any(Long.TYPE))).willReturn(trip);

            List<Object> itinerarys = new ArrayList<>();
            itinerarys.add(ItineraryAccommodationDTO.of("제주신라호텔", "제주 서귀포시 중문관광로72번길 75",
                LocalDateTime.of(2023, 10, 25, 15, 0), LocalDateTime.of(2023, 10, 26, 11, 0)));
            itinerarys.add(ItineraryTransportationDTO.of("카카오택시", "제주신라호텔", "제주 서귀포시 중문관광로72번길 75",
                "오설록 티 뮤지엄", "제주 서귀포시 안덕면 신화역사로 15 오설록", LocalDateTime.of(2023, 10, 26, 12, 0),
                LocalDateTime.of(2023, 10, 26, 13, 0)));
            itinerarys.add(ItineraryVisitDTO.of("카멜리아힐", "제주 서귀포시 안덕면 병악로 166",
                LocalDateTime.of(2023, 10, 26, 14, 0), LocalDateTime.of(2023, 10, 26, 16, 0)));

            // when
            List<Object> itineraryResponseList = itineraryGetDeleteService.getItineraryByTripId(
                trip.getId());

            // then
            assertThat(itineraryResponseList.get(0)).extracting("accommodationName",
                    "accommodationRoadAddressName", "checkIn", "checkOut")
                .containsExactly("제주신라호텔", "제주 서귀포시 중문관광로72번길 75",
                    LocalDateTime.of(2023, 10, 25, 15, 0), LocalDateTime.of(2023, 10, 26, 11, 0));
            verify(tripService, Mockito.times(1)).getTrip(trip.getId());

        }

    }

    @Nested
    @DisplayName("deleteItinerary()는")
    class Context_deleteItinerary {

        @Test
        @DisplayName("ItineraryId를 통해 Itinerary를 삭제할 수 있다.")
        void _willSuccess() throws NotFoundException {
            // given
            Long itineraryId = 1L;
            Trip trip = Trip.builder().id(1L).name("제주도 여행").startDate(LocalDate.of(2023, 10, 23))
                .endDate(LocalDate.of(2023, 10, 27)).isDomestic(true)
                .itineraries(new ArrayList<>()).build();
            Itinerary itinerary = new Itinerary(itineraryId, trip);
            when(itineraryRepository.findById(itineraryId)).thenReturn(Optional.of(itinerary));

            // when
            ItineraryGetResponseDTO itineraryGetResponseDTO = itineraryGetDeleteService.deleteItinerary(
                itineraryId);

            //then
            verify(itineraryRepository, times(1)).delete(itinerary);

        }
    }
}
