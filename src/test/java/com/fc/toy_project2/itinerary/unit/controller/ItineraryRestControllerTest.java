//package com.fc.toy_project2.itinerary.unit.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fc.toy_project2.domain.itinerary.controller.ItineraryGetDeleteController;
//import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
//import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
//import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
//import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(ItineraryGetDeleteController.class)
//public class ItineraryRestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    ItineraryGetDeleteService itineraryGetDeleteService;
//
//    @Nested
//    @DisplayName("getPlaceByKeyword()는")
//    class Context_getPlaceByKeyword {
//
//        @Test
//        @DisplayName("query를 톻해 장소를 조회할 수 있다.")
//        void _willSuccess() throws Exception {
//            // given
//            List<SearchResponseDTO> itinerarySearchList = new ArrayList<>();
//            itinerarySearchList.add(
//                SearchResponseDTO.builder().placeName("카카오 프렌즈 골프아카데미&스크린 파주운정점")
//                    .roadAddressName("경기 파주시 청암로17번길 59")
//                    .placeUrl("http://place.map.kakao.com/1888301706").build());
//            itinerarySearchList.add(
//                SearchResponseDTO.builder().placeName("카카오프렌즈 스크린골프 만성점")
//                    .roadAddressName("전북 전주시 덕진구 만성중앙로 53-46")
//                    .placeUrl("http://place.map.kakao.com/1480405278").build());
//            itinerarySearchList.add(
//                SearchResponseDTO.builder().placeName("양지카카오 프렌즈 스크린골프")
//                    .roadAddressName("경기 남양주시 오남읍 진건오남로830번길 14")
//                    .placeUrl("http://place.map.kakao.com/455587547").build());
//            itinerarySearchList.add(SearchResponseDTO.builder().placeName("카카오프렌즈스크린 금곡점")
//                .roadAddressName("경기 수원시 권선구 금호로 83-8")
//                .placeUrl("http://place.map.kakao.com/1052618040").build());
//            given(itineraryGetDeleteService.getPlaceByKeyword(any())).willReturn(
//                itinerarySearchList);
//
//            // when, then
//            mockMvc.perform(get("/api/itinerary/keyword/{query}", "카카오프렌즈"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").exists())
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data[0].placeName").exists())
//                .andExpect(jsonPath("$.data[0].roadAddressName").exists())
//                .andExpect(jsonPath("$.data[0].placeUrl").exists()).andDo(print());
//        }
//    }
//
//    @Nested
//    @DisplayName("getItineraryByTripId()는")
//    class Context_getItinerarySearch {
//
//        @Test
//        @DisplayName("tripId를 통해 Itinerary를 조회할 수 있다.")
//        void _willSuccess() throws Exception {
//            // given
//            List<Object> itinerarys = new ArrayList<>();
//            itinerarys.add(AccommodationResponseDTO.builder().accommodationName("제주신라호텔")
//                .accommodationRoadAddressName("제주 서귀포시 중문관광로72번길 75")
//                .checkIn(LocalDateTime.of(2023, 10, 25, 15, 0))
//                .checkOut(LocalDateTime.of(2023, 10, 26, 11, 0)).build());
//            itinerarys.add(TransportationResponseDTO.builder()
//                .transportation("카카오택시").departurePlace("제주신라호텔")
//                .departurePlaceRoadAddressName("제주 서귀포시 중문관광로72번길 75")
//                .destination("오설록 티 뮤지엄").destinationRoadAddressName("제주 서귀포시 안덕면 신화역사로 15 오설록")
//                .departureTime(LocalDateTime.of(2023, 10, 26, 12, 0))
//                .arrivalTime(LocalDateTime.of(2023, 10, 26, 13, 0)).build());
//            itinerarys.add(VisitResponseDTO.builder().placeName("카멜리아힐")
//                .placeRoadAddressName("제주 서귀포시 안덕면 병악로 166")
//                .visitDepartureTime(LocalDateTime.of(2023, 10, 26, 14, 0))
//                .visitArrivalTime(LocalDateTime.of(2023, 10, 26, 16, 0)).build());
//
//            given(itineraryGetDeleteService.getItineraryByTripId(any(Long.TYPE))).willReturn(
//                itinerarys);
//
//            // when, then
//            mockMvc.perform(get("/api/itinerary/{tripId}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").exists())
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data[0]").exists())
//                .andExpect(jsonPath("$.data[1]").exists())
//                .andExpect(jsonPath("$.data[2]").exists()).andDo(print());
//            verify(itineraryGetDeleteService, times(1)).getItineraryByTripId(any(Long.TYPE));
//
//        }
//    }
//
//    @Nested
//    @DisplayName("deleteItinerary()는")
//    class Context_deleteItinerary {
//
//        @Test
//        @DisplayName("여정 정보를 삭제할 수 있다.")
//        void _willSuccess() throws Exception {
//            // given
//            GetResponseDTO itinerary = GetResponseDTO.builder().itineraryId(1L)
//                .build();
//            given(itineraryGetDeleteService.deleteItinerary(any(Long.TYPE))).willReturn(itinerary);
//
//            // when, then
//            mockMvc.perform(delete("/api/itinerary/{itineraryId}", 1L))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        }
//    }
//}
