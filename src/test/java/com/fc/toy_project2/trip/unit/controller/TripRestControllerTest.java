package com.fc.toy_project2.trip.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.toy_project2.domain.trip.controller.TripRestController;
import com.fc.toy_project2.domain.trip.dto.request.PostTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.response.TripResponseDTO;
import com.fc.toy_project2.domain.trip.service.TripService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
            Mockito.doNothing().when(tripService).postTrip(postTripRequestDTO);

            // when, then
            mockMvc.perform(post("/api/trip")
                    .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());
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
                mockMvc.perform(post("/api/trip")
                        .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
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
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
                    .startDate(null).endDate("2023-10-26").isDomestic(true).build();

                // when, then
                mockMvc.perform(post("/api/trip")
                        .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 저장할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
                    .startDate(" ").endDate("2023-10-26").isDomestic(true).build();

                // when, then
                mockMvc.perform(post("/api/trip")
                        .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
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
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
                    .startDate("2023-10-26").endDate(null).isDomestic(true).build();

                // when, then
                mockMvc.perform(post("/api/trip")
                        .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).postTrip(any(PostTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 저장할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
                    .startDate("2023-10-26").endDate(" ").isDomestic(true).build();

                // when, then
                mockMvc.perform(post("/api/trip")
                        .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
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
                PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
                    .startDate("2023-10-26").endDate("2023-10-26").isDomestic(null).build();

                // when, then
                mockMvc.perform(post("/api/trip")
                        .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
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
            List<TripResponseDTO> trips = new ArrayList<>();
            trips.add(TripResponseDTO.builder().id(1L).name("제주도 여행").startDate("2023-10-23")
                .endDate("2023-10-27").isDomestic(true).build());
            trips.add(TripResponseDTO.builder().id(2L).name("속초 겨울바다 여행").startDate("2023-11-27")
                .endDate("2023-11-29").isDomestic(true).build());
            trips.add(TripResponseDTO.builder().id(3L).name("크리스마스 미국 여행").startDate("2023-12-24")
                .endDate("2023-12-26").isDomestic(false).build());
            given(tripService.getTrips()).willReturn(trips);

            // when, then
            mockMvc.perform(get("/api/trip")).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").exists())
                .andExpect(jsonPath("$.data[0].name").exists())
                .andExpect(jsonPath("$.data[0].startDate").exists())
                .andExpect(jsonPath("$.data[0].endDate").exists())
                .andExpect(jsonPath("$.data[0].isDomestic").exists()).andDo(print());
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
            TripResponseDTO trip = TripResponseDTO.builder().id(1L).name("제주도 여행")
                .startDate("2023-10-23").endDate("2023-10-27").isDomestic(true).build();
            given(tripService.getTripById(any(Long.TYPE))).willReturn(trip);

            // when, then
            mockMvc.perform(get("/api/trip/{tripId}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap()).andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.startDate").exists())
                .andExpect(jsonPath("$.data.endDate").exists())
                .andExpect(jsonPath("$.data.isDomestic").exists()).andDo(print());
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
            UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
                .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
            TripResponseDTO trip = TripResponseDTO.builder().id(1L).name("제주도 여행")
                .startDate("2023-10-23").endDate("2023-10-27").isDomestic(true).build();
            given(tripService.updateTrip(any(UpdateTripRequestDTO.class))).willReturn(trip);

            // when, then
            mockMvc.perform(
                    patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap()).andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").exists())
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
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(null)
                    .name("울릉도 여행").startDate("2023-10-25").endDate("2023-10-26").isDomestic(true)
                    .build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
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
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name(null)
                    .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
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
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
                    .startDate(null).endDate("2023-10-26").isDomestic(true).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 수정할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
                    .startDate(" ").endDate("2023-10-26").isDomestic(true).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
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
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
                    .startDate("2023-10-25").endDate(null).isDomestic(true).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }

            @Test
            @DisplayName("빈 칸일 경우 여행 정보를 수정할 수 없다.")
            void blank_willFail() throws Exception {
                // given
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
                    .startDate("2023-10-25").endDate(" ").isDomestic(true).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
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
                UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
                    .startDate("2023-10-25").endDate("2023-10-26").isDomestic(null).build();

                // when, then
                mockMvc.perform(
                        patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andDo(print());
                verify(tripService, never()).updateTrip(any(UpdateTripRequestDTO.class));
            }
        }
    }
}
