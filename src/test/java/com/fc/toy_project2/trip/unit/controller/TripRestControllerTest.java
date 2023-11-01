package com.fc.toy_project2.trip.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.trip.controller.TripRestController;
import com.fc.toy_project2.domain.trip.dto.request.PostTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.response.GetTripResponseDTO;
import com.fc.toy_project2.domain.trip.dto.response.GetTripsResponseDTO;
import com.fc.toy_project2.domain.trip.dto.response.ItineraryInfoDTO;
import com.fc.toy_project2.domain.trip.dto.response.TripResponseDTO;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.service.TripService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Trip REST Controller Test
 */
@WebMvcTest(TripRestController.class)
public class TripRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TripService tripService;

    @Nested
    @DisplayName("postTrip()은")
    class Context_postTrip {

        @Test
        @DisplayName("여행정보를 저장할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
                .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
            TripResponseDTO trip = TripResponseDTO.builder().tripId(1L).tripName("제주도 여행")
                .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
            given(tripService.postTrip(any(PostTripRequestDTO.class))).willReturn(trip);

            // when, then
            mockMvc.perform(post("/api/trips").content(
                        new ObjectMapper().writeValueAsString(postTripRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap()).andExpect(jsonPath("$.data.tripId").exists())
                .andExpect(jsonPath("$.data.tripName").exists())
                .andExpect(jsonPath("$.data.startDate").exists())
                .andExpect(jsonPath("$.data.endDate").exists())
                .andExpect(jsonPath("$.data.isDomestic").exists()).andDo(print());
            verify(tripService, times(1)).postTrip((any(PostTripRequestDTO.class)));
        }

        @Nested
        @DisplayName("name이 ")
        class Element_name {

            @Test
            @DisplayName("null일 경우 여행 정보를 저장할 수 없다.")
            void null_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName(null)
                    .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();

                // when, then
                mockMvc.perform(post("/api/trips").content(
                            new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("startDate가 ")
        class Element_startDate {

            @Test
            @DisplayName("null일 경우 여행 정보를 저장할 수 없다.")
            void null_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder()
                    .tripName("제주도 여행").startDate(null).endDate("2023-10-26").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(post("/api/trips").content(
                            new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 저장할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder()
                    .tripName("제주도 여행").startDate(" ").endDate("2023-10-26").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(post("/api/trips").content(
                            new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("endDate가 ")
        class Element_endDate {

            @Test
            @DisplayName("null일 경우 여행 정보를 저장할 수 없다.")
            void null_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder()
                    .tripName("제주도 여행").startDate("2023-10-26").endDate(null).isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(post("/api/trips").content(
                            new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 저장할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder()
                    .tripName("제주도 여행").startDate("2023-10-26").endDate(" ").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(post("/api/trips").content(
                            new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("isDomestic이 ")
        class Element_isDomestic {

            @Test
            @DisplayName("null일 경우 여행 정보를 저장할 수 없다.")
            void null_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder()
                    .tripName("제주도 여행").startDate("2023-10-26").endDate("2023-10-26")
                    .isDomestic(null).build();

                // when, then
                mockMvc.perform(post("/api/trips").content(
                            new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }
        }
    }

    @Nested
    @DisplayName("getTrips()은")
    class Context_getTrips {

        @Test
        @DisplayName("여행 정보 목록을 조회할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            List<ItineraryInfoDTO> itineraries = new ArrayList<>();
            itineraries.add(
                ItineraryInfoDTO.builder().itineraryId(1L).itineraryName("제주 신라 호텔에서 숙박!").build());
            itineraries.add(
                ItineraryInfoDTO.builder().itineraryId(2L).itineraryName("카카오 택시타고 이동!").build());
            itineraries.add(
                ItineraryInfoDTO.builder().itineraryId(3L).itineraryName("카멜리아힐 구경!").build());
            List<GetTripsResponseDTO> trips = new ArrayList<>();
            trips.add(
                GetTripsResponseDTO.builder().tripId(1L).tripName("제주도 여행").startDate("2023-10-23")
                    .endDate("2023-10-27").isDomestic(true).itineraries(itineraries).build());
            trips.add(GetTripsResponseDTO.builder().tripId(2L).tripName("속초 겨울바다 여행")
                .startDate("2023-11-27").endDate("2023-11-29").isDomestic(true).build());
            trips.add(GetTripsResponseDTO.builder().tripId(3L).tripName("크리스마스 미국 여행")
                .startDate("2023-12-24").endDate("2023-12-26").isDomestic(false).build());
            given(tripService.getTrips()).willReturn(trips);

            // when, then
            mockMvc.perform(get("/api/trips")).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].tripId").exists())
                .andExpect(jsonPath("$.data[0].tripName").exists())
                .andExpect(jsonPath("$.data[0].startDate").exists())
                .andExpect(jsonPath("$.data[0].endDate").exists())
                .andExpect(jsonPath("$.data[0].isDomestic").exists())
                .andExpect(jsonPath("$.data[0].itineraries").exists()).andDo(print());
            verify(tripService, times(1)).getTrips();
        }
    }

    @Nested
    @DisplayName("getTripById()는")
    class Context_getTripById {

        @Test
        @DisplayName("여행 정보를 조회할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            List<Object> itineraries = new ArrayList<>();
            itineraries.add(
                AccommodationResponseDTO.builder().itineraryId(1L).itineraryName("제주 신라 호텔에서 숙박!")
                    .accommodationName("제주신라호텔")
                    .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                    .checkIn("2023-10-25 15:00").checkOut("2023-10-26 11:00").build());
            itineraries.add(
                TransportationResponseDTO.builder().itineraryId(2L).itineraryName("카카오 택시타고 이동!")
                    .transportation("카카오택시").departurePlace("제주신라호텔")
                    .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75").destination("오설록 티 뮤지엄")
                    .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                    .departureTime("2023-10-26 12:00").arrivalTime("2023-10-26 13:00").build());
            itineraries.add(VisitResponseDTO.builder().itineraryId(3L).itineraryName("카멜리아힐 구경!")
                .placeName("카멜리아힐").placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .arrivalTime("2023-10-26 14:00").departureTime("2023-10-26, 16:00").build());
            GetTripResponseDTO trip = GetTripResponseDTO.builder().tripId(1L).tripName("제주도 여행")
                .startDate("2023-10-23").endDate("2023-10-27").isDomestic(true)
                .itineraries(itineraries).build();
            given(tripService.getTripById(any(Long.TYPE))).willReturn(trip);

            // when, then
            mockMvc.perform(get("/api/trips/{tripId}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap()).andExpect(jsonPath("$.data.tripId").exists())
                .andExpect(jsonPath("$.data.tripName").exists())
                .andExpect(jsonPath("$.data.startDate").exists())
                .andExpect(jsonPath("$.data.endDate").exists())
                .andExpect(jsonPath("$.data.isDomestic").exists())
                .andExpect(jsonPath("$.data.itineraries").exists()).andDo(print());
            verify(tripService, times(1)).getTripById(any(Long.TYPE));
        }
    }

    @Nested
    @DisplayName("updateTrip()은")
    class Context_updateTrip {

        @Test
        @DisplayName("여행 정보를 수정할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                .tripName("울릉도 여행").startDate("2023-10-25").endDate("2023-10-26").isDomestic(true)
                .build();
            TripResponseDTO trip = TripResponseDTO.builder().tripId(1L).tripName("제주도 여행")
                .startDate("2023-10-23").endDate("2023-10-27").isDomestic(true).build();
            given(tripService.updateTrip(any(UpdateTripRequestDTO.class))).willReturn(trip);

            // when, then
            mockMvc.perform(
                    patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap()).andExpect(jsonPath("$.data.tripId").exists())
                .andExpect(jsonPath("$.data.tripName").exists())
                .andExpect(jsonPath("$.data.startDate").exists())
                .andExpect(jsonPath("$.data.endDate").exists())
                .andExpect(jsonPath("$.data.isDomestic").exists()).andDo(print());
            verify(tripService, times(1)).updateTrip(any(UpdateTripRequestDTO.class));
        }

        @Nested
        @DisplayName("id가 ")
        class Element_id {

            @Test
            @DisplayName("null일 경우 여행 정보를 수정할 수 없다.")
            void null_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(null)
                    .tripName("울릉도 여행").startDate("2023-10-25").endDate("2023-10-26")
                    .isDomestic(true).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("name이 ")
        class Element_name {

            @Test
            @DisplayName("null일 경우 여행 정보를 수정할 수 없다.")
            void null_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                    .tripName(null).startDate("2023-10-25").endDate("2023-10-26").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("startDate가 ")
        class Element_startDate {

            @Test
            @DisplayName("null일 경우 여행 정보를 수정할 수 없다.")
            void null_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                    .tripName("울릉도 여행").startDate(null).endDate("2023-10-26").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 수정할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                    .tripName("울릉도 여행").startDate(" ").endDate("2023-10-26").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("endDate가 ")
        class Element_endDate {

            @Test
            @DisplayName("null일 경우 여행 정보를 수정할 수 없다.")
            void null_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                    .tripName("울릉도 여행").startDate("2023-10-25").endDate(null).isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 수정할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                    .tripName("울릉도 여행").startDate("2023-10-25").endDate(" ").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }
        }

        @Nested
        @DisplayName("isDomestic이 ")
        class Element_isDomestic {

            @Test
            @DisplayName("null일 경우 여행 정보를 수정할 수 없다.")
            void null_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().tripId(1L)
                    .tripName("울릉도 여행").startDate("2023-10-25").endDate("2023-10-26")
                    .isDomestic(null).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trips").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }
        }
    }

    @Nested
    @DisplayName("deleteTripById()는")
    class Context_deleteTripById {

        @Test
        @DisplayName("여행 정보를 삭제할 수 있다")
        void _willSuccess() throws Exception {
            //given
            List<Itinerary> itineraries = new ArrayList<>();
            itineraries.add(Accommodation.builder().id(1L).accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn(LocalDateTime.of(2023, 10, 25, 15, 0))
                .checkOut(LocalDateTime.of(2023, 10, 26, 11, 0)).build());
            itineraries.add(
                Transportation.builder().id(2L).transportation("카카오택시").departurePlace("제주신라호텔")
                    .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75").destination("오설록 티 뮤지엄")
                    .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                    .departureTime(LocalDateTime.of(2023, 10, 26, 12, 0))
                    .arrivalTime(LocalDateTime.of(2023, 10, 26, 13, 0)).build());
            itineraries.add(Visit.builder().id(3L).placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
                .departureTime(LocalDateTime.of(2023, 10, 26, 14, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 26, 16, 0)).build());
            Trip trip = Trip.builder().id(1L).name("제주도 여행").startDate(LocalDate.of(2023, 10, 23))
                .endDate(LocalDate.of(2023, 10, 27)).isDomestic(true).itineraries(itineraries)
                .build();
            given(tripService.getTrip(any(Long.TYPE))).willReturn(trip);

            //when, then
            mockMvc.perform(delete("/api/trips/{tripId}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andDo(print());
            verify(tripService, times(1)).deleteTripById(any(Long.TYPE));
        }
    }
}
