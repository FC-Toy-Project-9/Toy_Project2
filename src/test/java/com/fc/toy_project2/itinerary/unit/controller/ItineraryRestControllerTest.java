package com.fc.toy_project2.itinerary.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fc.toy_project2.domain.itinerary.controller.ItineraryGetDeleteController;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryGetResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitDTO;
import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
import com.fc.toy_project2.domain.trip.entity.Trip;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ItineraryGetDeleteController.class)
public class ItineraryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItineraryGetDeleteService itineraryGetDeleteService;

    @Nested
    @DisplayName("getItineraryByTripId()는")
    class Context_getItinerarySearch {

        @Test
        @DisplayName("TripId를 통해 Itinerary를 조회할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            List<Object> itinerarys = new ArrayList<>();
            itinerarys.add(ItineraryAccommodationDTO.of("제주신라호텔", "제주 서귀포시 중문관광로72번길 75",
                LocalDateTime.of(2023, 10, 25, 15, 0), LocalDateTime.of(2023, 10, 26, 11, 0)));
            itinerarys.add(ItineraryTransportationDTO.of("카카오택시", "제주신라호텔", "제주 서귀포시 중문관광로72번길 75",
                "오설록 티 뮤지엄", "제주 서귀포시 안덕면 신화역사로 15 오설록", LocalDateTime.of(2023, 10, 26, 12, 0),
                LocalDateTime.of(2023, 10, 26, 13, 0)));
            itinerarys.add(ItineraryVisitDTO.of("카멜리아힐", "제주 서귀포시 안덕면 병악로 166",
                LocalDateTime.of(2023, 10, 26, 14, 0), LocalDateTime.of(2023, 10, 26, 16, 0)));
            given(itineraryGetDeleteService.getItineraryByTripId(any(Long.TYPE))).willReturn(
                itinerarys);

            // when, then
            mockMvc.perform(get("/api/itinerary/search").param("tripId", "1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0]").exists())
                .andExpect(jsonPath("$.data[1]").exists())
                .andExpect(jsonPath("$.data[2]").exists());
            verify(itineraryGetDeleteService, times(1)).getItineraryByTripId(any(Long.TYPE));

        }
    }

    @Nested
    @DisplayName("deleteItinerary()는")
    class Context_deleteItinerary {

        @Test
        @DisplayName("여정 정보를 삭제할 수 있다.")
        void _willSuccess() throws Exception {
            // given, when, then
            Trip trip = Trip.builder().id(1L).name("우도 여행").startDate(LocalDate.of(2023, 10, 25))
                .endDate(LocalDate.of(2023, 10, 31)).build();
            ItineraryGetResponseDTO itinerary = ItineraryGetResponseDTO.of(1L, trip);

            mockMvc.perform(delete("/api/itinerary").param("itineraryId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        }


    }


}
