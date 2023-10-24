package com.fc.toy_project2.domain.itinerary.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fc.toy_project2.domain.itinerary.dto.ItinerarySearchResponseDto;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ItinerarySearchService {

    @Value("${kakao-api.api-url}")
    private String uri;

    @Value("${kakao-api.api-key}")
    private String key;

    private HttpEntity<String> httpEntity;

    @PostConstruct
    protected void init(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + key);
        httpEntity = new HttpEntity<>(headers);
    }

    public List<ItinerarySearchResponseDto> getPlaceByKeyword(String query) throws Exception {
        URI tmp = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("query", query)
            .queryParam("page", 5)
            .encode(StandardCharsets.UTF_8)
            .build().toUri();

        Assert.notNull(query,"query");
        ResponseEntity<String> response = new RestTemplate().exchange(tmp, HttpMethod.GET, httpEntity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody().toString());
        JSONArray jsonArray = jsonObject.getJSONArray("documents");
        List<ItinerarySearchResponseDto> itinerarySearchList = new ArrayList<>();
        for(int i = 0; i< 10; i++){
            JSONObject subJsonObj = jsonArray.getJSONObject(i);
            String placeName = subJsonObj.getString("place_name");
            String roadAddressName = subJsonObj.getString("road_address_name");
            String placeUrl = subJsonObj.getString("place_url");
            itinerarySearchList.add(ItinerarySearchResponseDto.of(placeName,roadAddressName,placeUrl));
        }

        return itinerarySearchList;

    }

}
