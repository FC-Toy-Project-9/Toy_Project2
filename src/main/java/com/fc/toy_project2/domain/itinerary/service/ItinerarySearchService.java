package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.ItinerarySearchResponseDto;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
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

    public ItinerarySearchResponseDto getPlaceByKeyword(String query) throws Exception {
        URI tmp = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("query", query)
            .queryParam("page", 5)
            .encode(StandardCharsets.UTF_8)
            .build().toUri();

        Assert.notNull(query,"query");
        ResponseEntity<ItinerarySearchResponseDto> response = new RestTemplate().exchange(tmp, HttpMethod.GET, httpEntity, ItinerarySearchResponseDto.class);
        return response.getBody();

    }

}
