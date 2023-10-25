package com.fc.toy_project2.domain.itinerary.dto;

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
