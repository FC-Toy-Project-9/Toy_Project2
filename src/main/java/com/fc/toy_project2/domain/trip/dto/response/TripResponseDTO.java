package com.fc.toy_project2.domain.trip.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 여행 정보 응답 DTO
 */
@Getter
@Builder
public class TripResponseDTO {

    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Boolean isDomestic;
}
