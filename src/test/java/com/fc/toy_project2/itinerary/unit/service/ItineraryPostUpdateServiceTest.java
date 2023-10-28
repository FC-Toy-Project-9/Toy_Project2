package com.fc.toy_project2.itinerary.unit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.toy_project2.domain.itinerary.dto.request.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.itinerary.service.ItineraryPostUpdateService;
import com.fc.toy_project2.domain.trip.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ItineraryPostUpdateService.class)
class ItineraryPostUpdateServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ItineraryPostUpdateService itineraryPostUpdateService;

    @MockBean
    TripService tripService;

    @MockBean
    ItineraryRepository itineraryRepository;

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
            AccommodationResponseDTO expectedAccommodation = AccommodationResponseDTO.builder()
                    .accommodationName("제주신라호텔")
                    .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                    .checkIn("2023-10-25 15:00:00")
                    .checkOut("2023-10-26 11:00:00")
                    .build();
            given(tripService.getTrip(any(Long.TYPE)));
            given(itineraryRepository.save(any(Itinerary.class)));

            // when
            AccommodationResponseDTO result = itineraryPostUpdateService.createdAccommodation(createDTO, 1L,1L);

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
        TransportationResponseDTO expectedResponse = TransportationResponseDTO.builder()
                .transportation("비행기")
                .departurePlace("인천공항")
                .departurePlaceRoadAddressName("인천광역시 중구 공항로 272")
                .destination("제주공항")
                .destinationRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime("2023-10-24 12:00:00")
                .arrivalTime("2023-10-25 12:00:00")
                .build();
        given(itineraryPostUpdateService.createdTransportation(any(ItineraryTransportationCreateDTO.class), eq(1L), eq(1L))).willReturn(expectedResponse);
        given(tripService.getTrip(any(Long.TYPE)));
        given(itineraryRepository.save(any(Itinerary.class)));
        // when
        TransportationResponseDTO result = itineraryPostUpdateService.createdTransportation(createDTO, 1L, 1L);

        // itineraryPostUpdateService.createTransportation()의 호출을 확인
        assertThat(result)
                .extracting("transportation", "departurePlace", "departurePlaceRoadAddressName",
                        "destination", "destinationRoadAddressName", "departureTime", "arrivalTime")
                .containsExactly("비행기", "인천공항", "인천광역시 중구 공항로 272", "제주공항",
                        "제주특별자치도 제주시 특별자치도, 공항로 2",
                        LocalDateTime.of(2023, 10, 24, 12, 0, 0), LocalDateTime.of(2023, 10, 25, 12, 0, 0));
        verify(itineraryRepository, times(1)).save(any(Itinerary.class));
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
        VisitResponseDTO expectedResponse = VisitResponseDTO.builder()
                .placeName("제주공항")
                .placeRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime("2023-10-24 12:00:00")
                .arrivalTime("2023-10-25 12:00:00")
                .build();
        given(tripService.getTrip(any(Long.TYPE)));
        given(itineraryRepository.save(any(Itinerary.class)));
        VisitResponseDTO result = itineraryPostUpdateService.createdVisit(createDTO, 1L, 1L);

        assertThat(result)
                .extracting("placeName", "placeRoadAddressName", "departureTime", "arrivalTime")
                .containsExactly("제주공항", "제주특별자치도 제주시 특별자치도, 공항로 2",
                        LocalDateTime.of(2023, 10, 24, 12, 0, 0), LocalDateTime.of(2023, 10, 25, 12, 0, 0));

        verify(itineraryRepository, times(1)).save(any(Itinerary.class));
    }

    @Test
    @DisplayName("체류 여정 정보를 수정할 수 있다.")
    void patchVisit_willSuccess() {
        ItineraryVisitCreateDTO createDTO = ItineraryVisitCreateDTO.builder()
                .placeName("제주공항")
                .placeRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime("2023-10-24 12:00:00")
                .arrivalTime("2023-10-25 12:00:00")
                .build();
        VisitResponseDTO expectedResponse = VisitResponseDTO.builder()
                .placeName("제주공항")
                .placeRoadAddressName("제주특별자치도 제주시 특별자치도, 공항로 2")
                .departureTime("2023-10-24 12:00:00")
                .arrivalTime("2023-10-25 12:00:00")
                .build();
        given(tripService.getTrip(any(Long.TYPE)));
        given(itineraryRepository.save(any(Itinerary.class)));
        VisitResponseDTO result = itineraryPostUpdateService.createdVisit(createDTO, 1L, eq(1L));

        assertThat(result)
                .extracting("placeName", "placeRoadAddressName", "departureTime", "arrivalTime")
                .containsExactly("제주공항", "제주특별자치도 제주시 특별자치도, 공항로 2",
                        LocalDateTime.of(2023, 10, 24, 12, 0, 0), LocalDateTime.of(2023, 10, 25, 12, 0, 0));

        verify(itineraryRepository, times(1)).save(any(Itinerary.class));
    }

    @Test
    @DisplayName("교통 여정 정보를 수정할 수 있다.")
    void patchTransportation_willSuccess() throws Exception {
        ItineraryTransportationCreateDTO createDTO = ItineraryTransportationCreateDTO.builder()
                .transportation("카카오택시")
                .departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄")
                .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 11:00:00")
                .arrivalTime("2023-10-26 13:00:00")
                .build();
        TransportationResponseDTO expectedResponse = TransportationResponseDTO.builder()
                .transportation("카카오택시")
                .departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄")
                .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 11:00:00")
                .arrivalTime("2023-10-26 13:00:00")
                .build();
        given(itineraryPostUpdateService.createdTransportation(any(ItineraryTransportationCreateDTO.class), eq(1L), eq(1L))).willReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/itinerary/transportation/{tripId}", 1L)
                        .content(new ObjectMapper().writeValueAsString(createDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.transportation").value("카카오택시"))
                .andExpect(jsonPath("$.data.departurePlace").value("제주신라호텔"))
                .andExpect(jsonPath("$.data.departurePlaceRoadAddressName").value("제주 서귀포시 중문관광로72번길 75"))
                .andExpect(jsonPath("$.data.destination").value("오설록 티 뮤지엄"))
                .andExpect(jsonPath("$.data.destinationRoadAddressName").value("제주 서귀포시 안덕면 신화역사로 15 오설록"))
                .andExpect(jsonPath("$.data.departureTime").value("2023-10-26 11:00:00"))
                .andExpect(jsonPath("$.data.arrivalTime").value("2023-10-26 13:00:00"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("숙박 여정 정보를 수정할 수 있다.")
    void patchAccommodation_willSuccess() throws Exception {
        ItineraryAccommodationCreateDTO createDTO = ItineraryAccommodationCreateDTO.builder()
                .accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn("2023-10-24 15:00:00")
                .checkOut("2023-10-25 10:00:00")
                .build();
        AccommodationResponseDTO expectedResponse = AccommodationResponseDTO.builder()
                .accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn("2023-10-24 15:00:00")
                .checkOut("2023-10-25 10:00:00")
                .build();
        given(itineraryPostUpdateService.createdAccommodation(any(ItineraryAccommodationCreateDTO.class), eq(1L),eq(1L))).willReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/itinerary/accommodation/{tripId}", 1L)
                        .content(new ObjectMapper().writeValueAsString(createDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.accommodationName").value("제주신라호텔"))
                .andExpect(jsonPath("$.data.accommodationRoadAddressName").value("제주 서귀포시 중문관광로72번길 75"))
                .andExpect(jsonPath("$.data.checkIn").value("2023-10-24T15:00:00"))
                .andExpect(jsonPath("$.data.checkOut").value("2023-10-25T10:00:00"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}