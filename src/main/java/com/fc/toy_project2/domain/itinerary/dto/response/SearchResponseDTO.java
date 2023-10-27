package com.fc.toy_project2.domain.itinerary.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchResponseDTO {

    private String placeName;
    private String roadAddressName;
    private String placeUrl;

    public static SearchResponseDTO of(String placeName, String roadAddressName,
                                       String placeUrl) {
        return SearchResponseDTO.builder()
                .placeName(placeName)
                .roadAddressName(roadAddressName)
                .placeUrl(placeUrl)
                .build();
    }

}