package com.fc.toy_project2.domain.itinerary.controller;

import com.fc.toy_project2.domain.itinerary.dto.ResultResponse;
import com.fc.toy_project2.domain.itinerary.service.ItinerarySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keyword")
@RequiredArgsConstructor
public class ItinerarySearchController {

    private final ItinerarySearchService itinerarySearchService;

    @GetMapping("")
    public ResponseEntity<ResultResponse> getItinerarySearch(@RequestParam String query)
        throws Exception {
        ResultResponse res = ResultResponse.builder()
            .code(HttpStatus.OK.value())
            .message("keyword search 성공")
            .result(itinerarySearchService.getPlaceByKeyword(query))
            .build();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
