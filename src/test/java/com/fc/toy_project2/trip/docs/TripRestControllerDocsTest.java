package com.fc.toy_project2.trip.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.toy_project2.domain.trip.controller.TripRestController;
import com.fc.toy_project2.domain.trip.dto.request.PostTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.response.TripResponseDTO;
import com.fc.toy_project2.domain.trip.service.TripService;
import com.fc.toy_project2.util.RestDocsSupport;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

/**
 * Trip REST API 문서화 Test
 */
public class TripRestControllerDocsTest extends RestDocsSupport {

    @MockBean
    private TripService tripService;

    @Override
    public Object initController() {
        return new TripRestController(tripService);
    }

    private final ConstraintDescriptions postDescriptions = new ConstraintDescriptions(PostTripRequestDTO.class);

    private final ConstraintDescriptions updateDescriptions = new ConstraintDescriptions(UpdateTripRequestDTO.class);

    @Test
    @DisplayName("postTrip()은 여행 정보를 저장할 수 있다.")
    void postTrip() throws Exception {
        // given
        PostTripRequestDTO postTripRequestDTO = PostTripRequestDTO.builder().tripName("제주도 여행")
            .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
        TripResponseDTO trip = TripResponseDTO.builder().id(1L).name("제주도 여행")
            .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
        given(tripService.postTrip(any(PostTripRequestDTO.class))).willReturn(trip);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/trip")
                .content(new ObjectMapper().writeValueAsString(postTripRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated()).andDo(restDoc.document(
                requestFields(
                    fieldWithPath("tripName").type(JsonFieldType.STRING).description("여행 이름")
                        .attributes(key("constraints").value(postDescriptions.descriptionsForProperty("tripName"))),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).description("여행 시작일")
                        .attributes(key("constraints").value(postDescriptions.descriptionsForProperty("startDate"))),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).description("여행 종료일")
                        .attributes(key("constraints").value(postDescriptions.descriptionsForProperty("endDate"))),
                    fieldWithPath("isDomestic").type(JsonFieldType.BOOLEAN).description("국내 여행 여부")
                        .attributes(key("constraints").value(postDescriptions.descriptionsForProperty("isDomestic")))),
                responseFields(responseCommon()).and(
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("여행 식별자"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("여행 이름"),
                    fieldWithPath("data.startDate").type(JsonFieldType.STRING).description("여행 시작일"),
                    fieldWithPath("data.endDate").type(JsonFieldType.STRING).description("여행 종료일"),
                    fieldWithPath("data.isDomestic").type(JsonFieldType.BOOLEAN).description("국내 여행 여부"))));
        verify(tripService, times(1)).postTrip((any(PostTripRequestDTO.class)));
    }

    @Test
    @DisplayName("getTrips()은 여행 정보 목록을 조회할 수 있다.")
    void getTrips() throws Exception {
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
        mockMvc.perform(get("/api/trip")).andExpect(status().isOk()).andDo(restDoc.document(
            responseFields(responseCommon()).and(
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("여행 식별자"),
                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("여행 이름"),
                fieldWithPath("data[].startDate").type(JsonFieldType.STRING).description("여행 시작일"),
                fieldWithPath("data[].endDate").type(JsonFieldType.STRING).description("여행 종료일"),
                fieldWithPath("data[].isDomestic").type(JsonFieldType.BOOLEAN)
                    .description("국내 여행 여부"))));
    }

    @Test
    @DisplayName("getTripById()는 여행 정보를 조회할 수 있다.")
    void getTripById() throws Exception {
        // given
        TripResponseDTO trip = TripResponseDTO.builder().id(1L).name("제주도 여행")
            .startDate("2023-10-23").endDate("2023-10-27").isDomestic(true).build();
        given(tripService.getTripById(any(Long.TYPE))).willReturn(trip);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/trip/{tripId}", 1L))
            .andExpect(status().isOk()).andDo(
                restDoc.document(pathParameters(parameterWithName("tripId").description("여행 식별자")),
                    responseFields(responseCommon()).and(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("여행 식별자"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("여행 이름"),
                        fieldWithPath("data.startDate").type(JsonFieldType.STRING)
                            .description("여행 시작일"),
                        fieldWithPath("data.endDate").type(JsonFieldType.STRING).description("여행 종료일"),
                        fieldWithPath("data.isDomestic").type(JsonFieldType.BOOLEAN)
                            .description("국내 여행 여부"))));
    }

    @Test
    @DisplayName("updateTrip()은 여행 정보를 수정할 수 있다.")
    void updateTrip() throws Exception {
        // given
        UpdateTripRequestDTO request = UpdateTripRequestDTO.builder().id(1L).name("울릉도 여행")
            .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
        TripResponseDTO trip = TripResponseDTO.builder().id(1L).name("제주도 여행")
            .startDate("2023-10-23").endDate("2023-10-27").isDomestic(true).build();
        given(tripService.updateTrip(any(UpdateTripRequestDTO.class))).willReturn(trip);

        // when, then
        mockMvc.perform(patch("/api/trip").content(new ObjectMapper().writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(
            restDoc.document(requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("여행 식별자").attributes(
                        key("constraints").value(updateDescriptions.descriptionsForProperty("id"))),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("여행 이름").attributes(
                        key("constraints").value(updateDescriptions.descriptionsForProperty("name"))),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).description("여행 시작일")
                        .attributes(key("constraints").value(
                            updateDescriptions.descriptionsForProperty("startDate"))),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).description("여행 종료일")
                        .attributes(key("constraints").value(
                            updateDescriptions.descriptionsForProperty("endDate"))),
                    fieldWithPath("isDomestic").type(JsonFieldType.BOOLEAN).description("국내 여행 여부")
                        .attributes(key("constraints").value(
                            updateDescriptions.descriptionsForProperty("isDomestic")))),
                responseFields(responseCommon()).and(
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("여행 식별자"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("여행 이름"),
                    fieldWithPath("data.startDate").type(JsonFieldType.STRING)
                        .description("여행 시작일"),
                    fieldWithPath("data.endDate").type(JsonFieldType.STRING).description("여행 종료일"),
                    fieldWithPath("data.isDomestic").type(JsonFieldType.BOOLEAN)
                        .description("국내 여행 여부"))));
        verify(tripService, times(1)).updateTrip(any(UpdateTripRequestDTO.class));
    }

    @Test
    @DisplayName("deleteTripById()은 특정 id를 가진 여행 정보를 삭제할 수 있다.")
    void deleteTripById() throws Exception {
        //given
        TripResponseDTO trip = TripResponseDTO.builder().id(1L).name("제주도 여행")
            .startDate("2023-10-25").endDate("2023-10-26").isDomestic(true).build();
        given(tripService.getTripById(any(Long.TYPE))).willReturn(trip);

        //when, then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/trip/{tripId}", 1L))
            .andExpect(status().isOk()).andDo(restDoc.document(
                pathParameters(parameterWithName("tripId").description("여행 식별자")),
                responseFields(responseCommon())));
        verify(tripService, times(1)).deleteTripById(any(Long.TYPE));
    }
}
