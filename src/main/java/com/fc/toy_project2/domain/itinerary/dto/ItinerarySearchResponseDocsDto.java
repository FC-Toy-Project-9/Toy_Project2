package com.fc.toy_project2.domain.itinerary.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarySearchResponseDocsDto {

    @JsonAlias("place_name")
    private String placeName;

    @JsonAlias("road_address_name")
    private String roadAddressName;

    @JsonAlias("place_url")
    private String placeUrl;

}
