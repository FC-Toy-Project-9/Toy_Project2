package com.fc.toy_project2.domain.itinerary.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItinerarySearchResponseDto {

    @JsonAlias("documents")
    private List<ItinerarySearchResponseDocsDto> itinerarySearchResponseDocsDto;


}
