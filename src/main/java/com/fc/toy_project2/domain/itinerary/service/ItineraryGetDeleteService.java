package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryDeleteResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItinerarySearchResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.exception.ItineraryNotFoundException;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional
@RequiredArgsConstructor
public class ItineraryGetDeleteService {

    private final ItineraryRepository itineraryRepository;

    @Value("${kakao-api.api-url}")
    private String uri;

    @Value("${kakao-api.api-key}")
    private String key;

    private HttpEntity<String> httpEntity;

    /**
     * Kakao Open API [키워드 검색하기] 를 위한 httpEntity를 생성하는 메서드
     */
    @PostConstruct
    protected void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + key);
        httpEntity = new HttpEntity<>(headers);
    }

    /**
     * qeury를 통해 Kakao Open API [키워드 검색하기] 결과값을 생성하는 메서드
     *
     * @param query 키워드
     * @return 장소명, 도로명, 장소 url이 담긴 List
     */
    public List<ItinerarySearchResponseDTO> getPlaceByKeyword(String query) throws Exception {
        URI tmp = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("query", query)
            .queryParam("page", 5)
            .encode(StandardCharsets.UTF_8)
            .build().toUri();

        Assert.notNull(query, "query");
        ResponseEntity<String> response = new RestTemplate().exchange(tmp, HttpMethod.GET,
            httpEntity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody().toString());
        JSONArray jsonArray = jsonObject.getJSONArray("documents");
        List<ItinerarySearchResponseDTO> itinerarySearchList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JSONObject documentObj = jsonArray.getJSONObject(i);
            itinerarySearchList.add(
                ItinerarySearchResponseDTO.builder().placeName(documentObj.getString("place_name"))
                    .roadAddressName(documentObj.getString("road_address_name"))
                    .placeUrl(documentObj.getString("place_url")).build());
        }
        return itinerarySearchList;
    }

    /**
     * tripId를 통해 여정정보를 조회하는 메서드
     *
     * @param tripId 여행 Id
     * @return 여정의 DTYPE에 따라 숙박,이동,체류DTO가 담긴 List
     */
    public List getItineraryByTripId(Long tripId) {
        List<Object> itineraryResponseList = new ArrayList<>();
        List<Itinerary> itineraryList = itineraryRepository.findAllByTripId(tripId);
        for (Itinerary itinerary : itineraryList) {
            if (itinerary.getType() == 0) {
                itineraryResponseList.add(AccommodationResponseDTO.builder()
                    .itineraryId(itinerary.getId())
                    .accommodationName(itinerary.getAccommodationName())
                    .accommodationRoadAddressName(
                        itinerary.getAccommodationRoadAddressName())
                    .checkIn(localDateTimeToString(itinerary.getCheckIn()))
                    .checkOut(localDateTimeToString(itinerary.getCheckOut()))
                    .build());
            } else if (itinerary.getType() == 1) {
                itineraryResponseList.add(TransportationResponseDTO.builder()
                    .itineraryId(itinerary.getId())
                    .transportation(itinerary.getTransportation())
                    .departurePlace(itinerary.getDeparturePlace())
                    .departurePlaceRoadAddressName(
                        itinerary.getDeparturePlaceRoadAddressName())
                    .destination(itinerary.getDestination())
                    .destinationRoadAddressName(itinerary.getDestinationRoadAddressName())
                    .departureTime(localDateTimeToString(itinerary.getDepartureTime()))
                    .arrivalTime(localDateTimeToString(itinerary.getArrivalTime())).build());
            } else if (itinerary.getType() == 2) {
                itineraryResponseList.add(TransportationResponseDTO.builder()
                    .itineraryId(itinerary.getId())
                    .transportation(itinerary.getTransportation())
                    .departurePlace(itinerary.getDeparturePlace())
                    .departurePlaceRoadAddressName(
                        itinerary.getDeparturePlaceRoadAddressName())
                    .destination(itinerary.getDestination())
                    .destinationRoadAddressName(itinerary.getDestinationRoadAddressName())
                    .departureTime(localDateTimeToString(itinerary.getDepartureTime()))
                    .arrivalTime(localDateTimeToString(itinerary.getArrivalTime())).build());
            }
        }
        return itineraryResponseList;
    }

    /**
     * itineraryId를 통해 여정정보를 삭제하는 메서드
     *
     * @param itineraryId 여정 Id
     * @return 삭제된 여정 정보
     */
    public ItineraryDeleteResponseDTO deleteItinerary(Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow(
            ItineraryNotFoundException::new);
        itineraryRepository.delete(itinerary);
        ItineraryDeleteResponseDTO itineraryDeleteResponseDTO = ItineraryDeleteResponseDTO.builder()
            .itineraryId(itinerary.getId()).build();
        return itineraryDeleteResponseDTO;
    }

    /**
     * LocalDateTime 타입을 String 타입으로 변환
     *
     * @param dateTime LocalDateTime 타입의 일시
     * @return String 타입의 일시
     */
    public String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM"));
        }
        return null;
    }
}
