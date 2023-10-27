package com.fc.toy_project2.itinerary.unit.service;

import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.itinerary.repository.AccommodationRepository;
import com.fc.toy_project2.domain.itinerary.repository.TransportationRepository;
import com.fc.toy_project2.domain.itinerary.repository.VisitRepository;
import com.fc.toy_project2.domain.itinerary.service.ItineraryPostUpdateService;
import com.fc.toy_project2.domain.trip.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@WebMvcTest(ItineraryPostUpdateService.class)
class ItineraryPostUpdateServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ItineraryPostUpdateService itineraryPostUpdateService;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private TransportationRepository transportationRepository;
    @Mock
    private VisitRepository visitRepository;

    @MockBean
    TripService tripService;

    @Nested
    @DisplayName("createAccommodation()은")
    class createAccommodation_ValidInput_ReturnsCreated {
        @Test
        @DisplayName("숙박 여정 정보를 저장할 수 있다.")
        void CreateAccommodationService_willSuccess() {
            ItineraryAccommodationCreateDTO createDTO = ItineraryAccommodationCreateDTO.builder()
                    .accommodationName("제주신라호텔")
                    .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                    .checkIn("2023-10-25 15:00:00")
                    .checkOut("2023-10-26 11:00:00")
                    .build();
            ItineraryAccommodationResponseDTO expectedAccommodation = ItineraryAccommodationResponseDTO.builder()
                    .accommodationName("제주신라호텔")
                    .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                    .checkIn(LocalDateTime.of(2023, 10, 25, 15, 0))
                    .checkOut(LocalDateTime.of(2023, 10, 26, 11, 0))
                    .build();
            given(tripService.getTrip(any(Long.TYPE)));
            given(accommodationRepository.save(any(Visit.class)));

            // when
            ItineraryAccommodationResponseDTO result = itineraryPostUpdateService.createAccommodation(createDTO, 1L);

            // then
            assertThat(result)
                    .extracting("accommodationName", "accommodationRoadAddressName", "checkIn", "checkOut")
                    .containsExactly("제주신라호텔", "제주 서귀포시 중문관광로72번길 75",
                            LocalDateTime.of(2023, 10, 25, 15, 0), LocalDateTime.of(2023, 10, 26, 11, 0));
        }
    }

    @Test
    @DisplayName("교통 여정 정보를 저장할 수 있다.")
    void createTransportation_willSuccess() {
        ItineraryTransportationCreateDTO createDTO = ItineraryTransportationCreateDTO.builder()
                .transportation("비행기")
                .departurePlace("인천공항")
                .departurePlaceRoadAddressName("인천광역시 중구 공항로 272")
                .destination("제주공항")
                .destinationRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime("2023-10-24 12:00:00")
                .arrivalTime("2023-10-25 12:00:00")
                .build();
        ItineraryTransportationResponseDTO expectedResponse = ItineraryTransportationResponseDTO.builder()
                .transportation("비행기")
                .departurePlace("인천공항")
                .departurePlaceRoadAddressName("인천광역시 중구 공항로 272")
                .destination("제주공항")
                .destinationRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime(LocalDateTime.of(2023, 10, 24, 12, 0, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 25, 12, 0, 0))
                .build();
        given(itineraryPostUpdateService.createTransportation(any(ItineraryTransportationCreateDTO.class), eq(1L))).willReturn(expectedResponse);
        given(tripService.getTrip(any(Long.TYPE)));
        given(transportationRepository.save(any(Visit.class)));
        // when
        ItineraryTransportationResponseDTO result = itineraryPostUpdateService.createTransportation(createDTO, 1L);

        // itineraryPostUpdateService.createTransportation()의 호출을 확인
        assertThat(result)
                .extracting("transportation", "departurePlace", "departurePlaceRoadAddressName",
                        "destination", "destinationRoadAddressName", "departureTime", "arrivalTime")
                .containsExactly("비행기", "인천공항", "인천광역시 중구 공항로 272", "제주공항",
                        "제주특별자치도 제주시 특별자치도, 공항로 2",
                        LocalDateTime.of(2023, 10, 24, 12, 0, 0), LocalDateTime.of(2023, 10, 25, 12, 0, 0));
        verify(transportationRepository, times(1)).save(any(Transportation.class));
    }

    @Test
    @DisplayName("체류 여정 정보를 저장할 수 있다.")
    void createVisit_willSuccess() {
        ItineraryVisitCreateDTO createDTO = ItineraryVisitCreateDTO.builder()
                .placeName("제주공항")
                .placeRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime("2023-10-24 12:00:00")
                .arrivalTime("2023-10-25 12:00:00")
                .build();
        ItineraryVisitResponseDTO expectedResponse = ItineraryVisitResponseDTO.builder()
                .placeName("제주공항")
                .placeRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime(LocalDateTime.of(2023, 10, 24, 12, 0, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 25, 12, 0, 0))
                .build();
        given(tripService.getTrip(any(Long.TYPE)));
        given(transportationRepository.save(any(Visit.class)));
        ItineraryVisitResponseDTO result = itineraryPostUpdateService.createVisit(createDTO, 1L);

        assertThat(result)
                .extracting("placeName", "placeRoadAddressName", "departureTime", "arrivalTime")
                .containsExactly("제주공항", "제주특별자치도 제주시 특별자치도, 공항로 2",
                        LocalDateTime.of(2023, 10, 24, 12, 0, 0), LocalDateTime.of(2023, 10, 25, 12, 0, 0));

        verify(visitRepository, times(1)).save(any(Visit.class));
    }
}