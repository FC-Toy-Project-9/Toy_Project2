package com.fc.toy_project2.domain.itinerary.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItinerarySearchResponseDto {

    private String placeName;
    private String roadAddressName;
    private String placeUrl;

    public static ItinerarySearchResponseDto of(String placeName, String roadAddressName,
        String placeUrl) {
        return ItinerarySearchResponseDto.builder()
            .placeName(placeName)
            .roadAddressName(roadAddressName)
            .placeUrl(placeUrl)
            .build();
    }


}
