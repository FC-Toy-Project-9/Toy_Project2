package com.fc.toy_project2.domain.itinerary.service;

import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryGetResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItinerarySearchResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitDTO;
import com.fc.toy_project2.domain.itinerary.entity.Accommodation;
import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.itinerary.entity.Transportation;
import com.fc.toy_project2.domain.itinerary.entity.Visit;
import com.fc.toy_project2.domain.itinerary.exception.ItineraryNotFoundException;
import com.fc.toy_project2.domain.itinerary.repository.ItineraryRepository;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.domain.trip.service.TripService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional
@RequiredArgsConstructor
public class ItineraryGetDeleteService {

    private final TripService tripService;
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
        Trip trip = tripService.getTrip(tripId);
        List<Object> itineraryResponseList = new ArrayList<>();
        if (trip.getItineraries().isEmpty()) {
            throw new ItineraryNotFoundException();
        }
        List<Itinerary> itineraryList = trip.getItineraries();
        for (Itinerary itinerary : itineraryList) {
            if (itinerary instanceof Accommodation) {
                Accommodation accommodation = ((Accommodation) itinerary);
                itineraryResponseList.add(ItineraryAccommodationDTO.builder()
                    .accommodationName(accommodation.getAccommodationName())
                    .accommodationRoadAddressName(accommodation.getAccommodationRoadAddressName())
                    .checkIn(accommodation.getCheckIn()).checkOut(accommodation.getCheckOut())
                    .build());


            } else if (itinerary instanceof Transportation) {
                Transportation transportation = ((Transportation) itinerary);
                itineraryResponseList.add(ItineraryTransportationDTO.builder()
                    .transportation(transportation.getTransportation())
                    .departurePlace(transportation.getDeparturePlace())
                    .departurePlaceRoadAddressName(
                        transportation.getDeparturePlaceRoadAddressName())
                    .destination(transportation.getDestination())
                    .destinationRoadAddressName(transportation.getDestinationRoadAddressName())
                    .departureTime(transportation.getDepartureTime())
                    .arrivalTime(transportation.getArrivalTime()).build());


            } else if (itinerary instanceof Visit) {
                Visit visit = ((Visit) itinerary);
                itineraryResponseList.add(
                    ItineraryVisitDTO.builder().placeName(visit.getPlaceName())
                        .placeRoadAddressName(visit.getPlaceRoadAddressName())
                        .visitArrivalTime(visit.getArrivalTime())
                        .visitDepartureTime(visit.getDepartureTime()).build());
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
    public ItineraryGetResponseDTO deleteItinerary(Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow(
            ItineraryNotFoundException::new);
        itineraryRepository.delete(itinerary);
        ItineraryGetResponseDTO itineraryGetResponseDTO = ItineraryGetResponseDTO.builder()
            .itineraryId(itinerary.getId()).build();

        return itineraryGetResponseDTO;

    }
}
