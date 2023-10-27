package com.fc.toy_project2.itinerary.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.toy_project2.domain.itinerary.controller.ItineraryPostController;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.service.ItineraryPostUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ItineraryPostController.class)
class ItineraryPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItineraryPostUpdateService itineraryPostUpdateService;

    @Nested
    @DisplayName("postItinerary()은")
    class Context_postTrip {
        @Test
        @DisplayName("숙박 여정 정보를 저장할 수 있다.")
        void createAccommodation_willSuccess() throws Exception {
            ItineraryAccommodationCreateDTO createDTO = ItineraryAccommodationCreateDTO.builder()
                    .accommodationName("제주신라호텔")
                    .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                    .checkIn("2023-10-25 15:00:00")
                    .checkOut("2023-10-26 11:00:00")
                    .build();
            AccommodationResponseDTO expectedResponse = AccommodationResponseDTO.builder()
                    .accommodationName("제주신라호텔")
                    .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                    .checkIn(LocalDateTime.of(2023, 10, 25, 15, 0))
                    .checkOut(LocalDateTime.of(2023, 10, 26, 11, 0))
                    .build();
            given(itineraryPostUpdateService.createAccommodation(any(ItineraryAccommodationCreateDTO.class), eq(1L))).willReturn(expectedResponse);

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
                    .andExpect(jsonPath("$.data.checkIn").value("2023-10-25T15:00:00"))
                    .andExpect(jsonPath("$.data.checkOut").value("2023-10-26T11:00:00"))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andDo(print());
        }
    }

    @Test
    @DisplayName("교통 여정 정보를 저장할 수 있다.")
    void createTransportation_willSuccess() throws Exception {
        ItineraryTransportationCreateDTO createDTO = ItineraryTransportationCreateDTO.builder()
                .transportation("카카오택시")
                .departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄")
                .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 12:00:00")
                .arrivalTime("2023-10-26 13:00:00")
                .build();
        TransportationResponseDTO expectedResponse = TransportationResponseDTO.builder()
                .transportation("카카오택시")
                .departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄")
                .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime(String.valueOf(LocalDateTime.of(2023, 10, 26, 12, 0, 0)))
                .arrivalTime(String.valueOf(LocalDateTime.of(2023, 10, 26, 13, 0, 0)))
                .build();
        given(itineraryPostUpdateService.createTransportation(any(ItineraryTransportationCreateDTO.class), eq(1L))).willReturn(expectedResponse);

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
                .andExpect(jsonPath("$.data.departureTime").value("2023-10-26 12:00:00"))
                .andExpect(jsonPath("$.data.arrivalTime").value("2023-10-26 13:00:00"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("체류 여정 정보를 저장할 수 있다.")
    void createVisittation_willSuccess() throws Exception {
        ItineraryVisitCreateDTO createDTO = ItineraryVisitCreateDTO.builder()
                .placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .departureTime("2023-10-26 14:00:00")
                .arrivalTime("2023-10-26 16:00:00")
                .build();
        VisitResponseDTO expectedResponse = VisitResponseDTO.builder()
                .placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .departureTime(LocalDateTime.of(2023, 10, 26, 14, 0, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 26, 16, 0, 0))
                .build();
        given(itineraryPostUpdateService.createVisit(any(ItineraryVisitCreateDTO.class), eq(1L))).willReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/itinerary/visit/{tripId}", 1L)
//                        .content(asJsonString(createDTO))
                        .content(new ObjectMapper().writeValueAsString(createDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.placeName").value("카멜리아힐"))
                .andExpect(jsonPath("$.data.placeRoadAddressName").value("제주 서귀포시 안덕면 병악로 166"))
                .andExpect(jsonPath("$.data.departureTime").value("2023-10-26 14:00:00"))
                .andExpect(jsonPath("$.data.arrivalTime").value("2023-10-26 16:00:00"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}