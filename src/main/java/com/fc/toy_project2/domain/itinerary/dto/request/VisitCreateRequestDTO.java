package com.fc.toy_project2.domain.itinerary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitCreateRequestDTO {

    @NotNull(message = "여행 ID를 입력하세요.")
    private Long tripId;
    @NotBlank(message = "여정 이름을 입력하세요.")
    private String itineraryName;
    @NotBlank(message = "장소 이름을 입력하세요.")
    private String placeName;
    @NotBlank(message = "장소 도로명 주소를 입력하세요.")
    private String placeRoadAddressName;
    @NotBlank(message = "도착 일시를 입력하세요.(yyyy-MM-dd HH:ss)")
    private String arrivalTime;
    @NotBlank(message = "출발 일시를 입력하세요.(yyyy-MM-dd HH:ss)")
    private String departureTime;
}