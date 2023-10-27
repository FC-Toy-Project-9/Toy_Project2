package com.fc.toy_project2.itinerary.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryDeleteResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.exception.ItineraryNotFoundException;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.exception.TripNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
public class ItineraryServiceTest {

    @InjectMocks
    private ItineraryGetDeleteService itineraryGetDeleteService;

    @Mock
    private ItineraryRepository itineraryRepository;

    @Mock
    private TripService tripService;

    @Nested
    @DisplayName("getItineraryByTripId()는")
    class Context_getItineraryByTripId {

        @Test
        @DisplayName("tripId를 통해 Itinerary를 조회할 수 있다.")
        void _willSuccess() throws TripNotFoundException {
            // given
            Itinerary accommodation = Itinerary.builder().id(1L).accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn(LocalDateTime.of(2023, 10, 25, 15, 0))
                .checkOut(LocalDateTime.of(2023, 10, 26, 11, 0)).build();

            Itinerary transportation = Itinerary.builder().id(2L)
                .transportation("카카오택시").departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime(LocalDateTime.of(2023, 10, 26, 12, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 26, 13, 0)).build();

            Itinerary visit = Itinerary.builder().id(3L).placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .departureTime(LocalDateTime.of(2023, 10, 26, 14, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 26, 16, 0)).build();

            List<Itinerary> itineraryList = new ArrayList<>();
            itineraryList.add(accommodation);
            itineraryList.add(transportation);
            itineraryList.add(visit);

            List<Object> itinerarys = new ArrayList<>();
            itinerarys.add(AccommodationResponseDTO.builder().accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn(itineraryGetDeleteService.localDateTimeToString(LocalDateTime.of(2023, 10, 25, 15, 0)))
                .checkOut(itineraryGetDeleteService.localDateTimeToString(LocalDateTime.of(2023, 10, 26, 11, 0))).build());
            itinerarys.add(TransportationResponseDTO.builder()
                .transportation("카카오택시").departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime(itineraryGetDeleteService.localDateTimeToString(LocalDateTime.of(2023, 10, 26, 12, 0)))
                .arrivalTime(itineraryGetDeleteService.localDateTimeToString(LocalDateTime.of(2023, 10, 26, 13, 0))));
            itinerarys.add(VisitResponseDTO.builder().placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .departureTime(itineraryGetDeleteService.localDateTimeToString(LocalDateTime.of(2023, 10, 26, 14, 0)))
                .arrivalTime(itineraryGetDeleteService.localDateTimeToString(LocalDateTime.of(2023, 10, 26, 16, 0))));
            given(itineraryRepository.findAllByTripId(any(Long.TYPE))).willReturn(itineraryList);

            // when
            List<Object> itineraryResponseList = itineraryGetDeleteService.getItineraryByTripId(1L);

            // then
            assertThat(itineraryResponseList.get(0)).extracting("accommodationName",
                    "accommodationRoadAddressName")
                .containsExactly("제주신라호텔", "제주 서귀포시 중문관광로72번길 75");
        }

    }

    @Nested
    @DisplayName("deleteItinerary()는")
    class Context_deleteItinerary {

        @Test
        @DisplayName("ItineraryId를 통해 Itinerary를 삭제할 수 있다.")
        void _willSuccess() throws ItineraryNotFoundException {
            // given
            Long itineraryId = 1L;
            Trip trip = Trip.builder().id(1L).name("제주도 여행").startDate(LocalDate.of(2023, 10, 23))
                .endDate(LocalDate.of(2023, 10, 27)).isDomestic(true)
                .itineraries(new ArrayList<>()).build();
            Itinerary itinerary = Itinerary.builder().id(1L).trip(trip).build();
            when(itineraryRepository.findById(itineraryId)).thenReturn(Optional.of(itinerary));

            // when
            ItineraryDeleteResponseDTO itineraryGetResponseDTO = itineraryGetDeleteService.deleteItinerary(
                itineraryId);

            //then
            verify(itineraryRepository, times(1)).delete(itinerary);

        }
    }
}
