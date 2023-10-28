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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.toy_project2.domain.itinerary.controller.ItineraryRestController;
import com.fc.toy_project2.domain.itinerary.dto.request.AccommodationCreateRequestDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.AccommodationUpdateRequestDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.TransportationCreateRequestDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.TransportationUpdateRequestDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.VisitCreateRequestDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.VisitUpdateRequestDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryDeleteResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItinerarySearchResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.service.ItineraryService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ItineraryRestController.class)
public class ItineraryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItineraryService itineraryService;

    @Nested
    @DisplayName("getPlaceByKeyword()는")
    class Context_getPlaceByKeyword {

        @Test
        @DisplayName("query를 톻해 장소를 조회할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            List<ItinerarySearchResponseDTO> itinerarySearchList = new ArrayList<>();
            itinerarySearchList.add(
                ItinerarySearchResponseDTO.builder().placeName("카카오 프렌즈 골프아카데미&스크린 파주운정점")
                    .roadAddressName("경기 파주시 청암로17번길 59")
                    .placeUrl("http://place.map.kakao.com/1888301706").build());
            itinerarySearchList.add(
                ItinerarySearchResponseDTO.builder().placeName("카카오프렌즈 스크린골프 만성점")
                    .roadAddressName("전북 전주시 덕진구 만성중앙로 53-46")
                    .placeUrl("http://place.map.kakao.com/1480405278").build());
            itinerarySearchList.add(
                ItinerarySearchResponseDTO.builder().placeName("양지카카오 프렌즈 스크린골프")
                    .roadAddressName("경기 남양주시 오남읍 진건오남로830번길 14")
                    .placeUrl("http://place.map.kakao.com/455587547").build());
            itinerarySearchList.add(ItinerarySearchResponseDTO.builder().placeName("카카오프렌즈스크린 금곡점")
                .roadAddressName("경기 수원시 권선구 금호로 83-8")
                .placeUrl("http://place.map.kakao.com/1052618040").build());
            given(itineraryService.getPlaceByKeyword(any())).willReturn(itinerarySearchList);

            // when, then
            mockMvc.perform(get("/api/itineraries/keyword/{query}", "카카오프렌즈"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeName").exists())
                .andExpect(jsonPath("$.data[0].roadAddressName").exists())
                .andExpect(jsonPath("$.data[0].placeUrl").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("createAccommodation()은")
    class Context_createAccommodation {

        @Test
        @DisplayName("숙박 여정 정보를 저장할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            AccommodationCreateRequestDTO request = AccommodationCreateRequestDTO.builder()
                .tripId(1L).itineraryName("제주여정1").accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75").checkIn("2023-10-25 15:00")
                .checkOut("2023-10-26 11:00").build();
            AccommodationResponseDTO accommodationResponseDTO = AccommodationResponseDTO.builder()
                .itineraryId(1L).itineraryName("제주여정1").accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75").checkIn("2023-10-25 15:00:00")
                .checkOut("2023-10-26 11:00:00").build();
            given(itineraryService.createAccommodation(
                any(AccommodationCreateRequestDTO.class))).willReturn(accommodationResponseDTO);

            // when, then
            mockMvc.perform(MockMvcRequestBuilders.post("/api/itineraries/accommodations")
                    .content(new ObjectMapper().writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itineraryId").exists())
                .andExpect(jsonPath("$.data.itineraryName").exists())
                .andExpect(jsonPath("$.data.accommodationName").exists())
                .andExpect(jsonPath("$.data.accommodationRoadAddressName").exists())
                .andExpect(jsonPath("$.data.checkIn").exists())
                .andExpect(jsonPath("$.data.checkOut").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("createTransportation()은")
    class Context_createTransportation {

        @Test
        @DisplayName("이동 여정 정보를 저장할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            TransportationCreateRequestDTO request = TransportationCreateRequestDTO.builder()
                .tripId(1L).itineraryName("제주여정2").transportation("카카오택시").departurePlace("제주신라호텔")
                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75").destination("오설록 티 뮤지엄")
                .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 12:00").arrivalTime("2023-10-26 13:00").build();
            TransportationResponseDTO transportationResponseDTO = TransportationResponseDTO.builder()
                .itineraryId(2L).itineraryName("제주여정2").transportation("카카오택시")
                .departurePlace("제주신라호텔").departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 12:00").arrivalTime("2023-10-26 13:00").build();
            given(itineraryService.createTransportation(
                any(TransportationCreateRequestDTO.class))).willReturn(transportationResponseDTO);

            // when, then
            mockMvc.perform(MockMvcRequestBuilders.post("/api/itineraries/transportations")
                    .content(new ObjectMapper().writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.itineraryId").exists())
                .andExpect(jsonPath("$.data.itineraryName").exists())
                .andExpect(jsonPath("$.data.transportation").exists())
                .andExpect(jsonPath("$.data.departurePlace").exists())
                .andExpect(jsonPath("$.data.departurePlaceRoadAddressName").exists())
                .andExpect(jsonPath("$.data.destination").exists())
                .andExpect(jsonPath("$.data.destinationRoadAddressName").exists())
                .andExpect(jsonPath("$.data.departureTime").exists())
                .andExpect(jsonPath("$.data.arrivalTime").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("createVisit()은")
    class Context_createVisit {

        @Test
        @DisplayName("체류 여정 정보를 저장할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            VisitCreateRequestDTO request = VisitCreateRequestDTO.builder().tripId(1L)
                .itineraryName("제주여정3").placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166").departureTime("2023-10-26 14:00")
                .arrivalTime("2023-10-26 16:00").build();
            VisitResponseDTO visitResponseDTO = VisitResponseDTO.builder().itineraryId(1L)
                .itineraryName("제주여정3").placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166").departureTime("2023-10-26 14:00")
                .arrivalTime("2023-10-26 16:00").build();
            given(itineraryService.createVisit(any(VisitCreateRequestDTO.class))).willReturn(
                visitResponseDTO);

            // when, then
            mockMvc.perform(MockMvcRequestBuilders.post("/api/itineraries/visits")
                    .content(new ObjectMapper().writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itineraryId").exists())
                .andExpect(jsonPath("$.data.itineraryName").exists())
                .andExpect(jsonPath("$.data.placeName").exists())
                .andExpect(jsonPath("$.data.placeRoadAddressName").exists())
                .andExpect(jsonPath("$.data.departureTime").exists())
                .andExpect(jsonPath("$.data.arrivalTime").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("getItineraryByTripId()는")
    class Context_getItinerarySearch {

        @Test
        @DisplayName("tripId를 통해 Itinerary를 조회할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            List<Object> itinerarys = new ArrayList<>();
            itinerarys.add(AccommodationResponseDTO.builder().itineraryId(1L).itineraryName("제주여정1")
                .accommodationName("제주신라호텔").accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .checkIn("2023-10-25 15:00").checkOut("2023-10-26 11:00").build());
            itinerarys.add(
                TransportationResponseDTO.builder().itineraryId(2L).itineraryName("제주여정2")
                    .transportation("카카오택시").departurePlace("제주신라호텔")
                    .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75").destination("오설록 티 뮤지엄")
                    .destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                    .departureTime("2023-10-26 12:00").arrivalTime("2023-10-26 13:00").build());
            itinerarys.add(
                VisitResponseDTO.builder().itineraryId(3L).itineraryName("제주여정3").placeName("카멜리아힐")
                    .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166").departureTime("2023-10-26 14:00")
                    .arrivalTime("2023-10-26 16:00").build());

            given(itineraryService.getItineraryByTripId(any(Long.TYPE))).willReturn(itinerarys);

            // when, then
            mockMvc.perform(get("/api/itineraries/{tripId}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isArray()).andExpect(jsonPath("$.data[0]").exists())
                .andExpect(jsonPath("$.data[1]").exists()).andExpect(jsonPath("$.data[2]").exists())
                .andDo(print());
            verify(itineraryService, times(1)).getItineraryByTripId(any(Long.TYPE));

        }
    }

    @Nested
    @DisplayName("updateAccommodation()은")
    class Context_updateAccommodation {

        @Test
        @DisplayName("숙박 여정 정보를 수정할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            AccommodationUpdateRequestDTO request = AccommodationUpdateRequestDTO.builder()
                .itineraryId(1L).itineraryName("즐거운 제주여정1").accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75").checkIn("2023-10-25 15:00")
                .checkOut("2023-10-26 11:00").build();
            AccommodationResponseDTO accommodationResponseDTO = AccommodationResponseDTO.builder()
                .itineraryId(1L).itineraryName("즐거운 제주여정1").accommodationName("제주신라호텔")
                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75").checkIn("2023-10-25 15:00")
                .checkOut("2023-10-26 11:00").build();
            given(itineraryService.updateAccommodation(
                any(AccommodationUpdateRequestDTO.class))).willReturn(accommodationResponseDTO);

            // when, then
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/itineraries/accommodations")
                    .content(new ObjectMapper().writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itineraryId").exists())
                .andExpect(jsonPath("$.data.itineraryName").exists())
                .andExpect(jsonPath("$.data.accommodationName").exists())
                .andExpect(jsonPath("$.data.accommodationRoadAddressName").exists())
                .andExpect(jsonPath("$.data.checkIn").exists())
                .andExpect(jsonPath("$.data.checkOut").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("updateTransportation()은")
    class Context_updateTransportation {

        @Test
        @DisplayName("이동 여정 정보를 수정할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            TransportationUpdateRequestDTO request = TransportationUpdateRequestDTO.builder()
                .itineraryId(1L).itineraryId(1L).itineraryName("즐거운 제주여정2").transportation("카카오택시")
                .departurePlace("제주신라호텔").departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 11:00").arrivalTime("2023-10-26 13:00").build();
            TransportationResponseDTO transportationResponseDTO = TransportationResponseDTO.builder()
                .itineraryId(1L).itineraryName("즐거운 제주여정2").transportation("카카오택시")
                .departurePlace("제주신라호텔").departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
                .departureTime("2023-10-26 11:00").arrivalTime("2023-10-26 13:00").build();
            given(itineraryService.updateTransportation(
                any(TransportationUpdateRequestDTO.class))).willReturn(transportationResponseDTO);

            // when, then
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/itineraries/transportations")
                    .content(new ObjectMapper().writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itineraryId").exists())
                .andExpect(jsonPath("$.data.itineraryName").exists())
                .andExpect(jsonPath("$.data.transportation").exists())
                .andExpect(jsonPath("$.data.departurePlace").exists())
                .andExpect(jsonPath("$.data.departurePlaceRoadAddressName").exists())
                .andExpect(jsonPath("$.data.destination").exists())
                .andExpect(jsonPath("$.data.destinationRoadAddressName").exists())
                .andExpect(jsonPath("$.data.departureTime").exists())
                .andExpect(jsonPath("$.data.arrivalTime").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("updateVisit()은")
    class Context_updateVisit {

        @Test
        @DisplayName("체류 여정 정보를 수정할 수 있다.")
        void _willSuccess() throws Exception {
            VisitUpdateRequestDTO request = VisitUpdateRequestDTO.builder().itineraryId(1L)
                .itineraryName("즐거운 제주여정3").placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166").departureTime("2023-10-26 14:00")
                .arrivalTime("2023-10-26 16:00").build();
            VisitResponseDTO visitResponseDTO = VisitResponseDTO.builder().itineraryId(1L)
                .itineraryName("즐거운 제주여정3").placeName("카멜리아힐")
                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166").departureTime("2023-10-26 14:00")
                .arrivalTime("2023-10-26 16:00").build();
            given(itineraryService.updateVisit(any(VisitUpdateRequestDTO.class))).willReturn(
                visitResponseDTO);

            mockMvc.perform(MockMvcRequestBuilders.patch("/api/itineraries/visits")
                    .content(new ObjectMapper().writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").exists()).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itineraryId").exists())
                .andExpect(jsonPath("$.data.itineraryName").exists())
                .andExpect(jsonPath("$.data.placeName").exists())
                .andExpect(jsonPath("$.data.placeRoadAddressName").exists())
                .andExpect(jsonPath("$.data.departureTime").exists())
                .andExpect(jsonPath("$.data.arrivalTime").exists()).andDo(print());
        }
    }

    @Nested
    @DisplayName("deleteItinerary()는")
    class Context_deleteItinerary {

        @Test
        @DisplayName("여정 정보를 삭제할 수 있다.")
        void _willSuccess() throws Exception {
            // given
            ItineraryDeleteResponseDTO itinerary = ItineraryDeleteResponseDTO.builder()
                .itineraryId(1L).build();
            given(itineraryService.deleteItinerary(any(Long.TYPE))).willReturn(itinerary);

            // when, then
            mockMvc.perform(delete("/api/itineraries/{itineraryId}", 1L)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        }
    }
}
