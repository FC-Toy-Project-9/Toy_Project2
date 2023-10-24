package com.fc.toy_project2.domain.itinerary.controller;


import com.fc.toy_project2.domain.itinerary.dto.ResultResponse;
import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/itinerary")
public class ItineraryGetDeleteController {

    private final ItineraryGetDeleteService itineraryGetDeleteService;

    @GetMapping("")
    public ResponseEntity<ResultResponse> getItineraryByTripId(@RequestParam long tripId){
        ResultResponse res = ResultResponse.builder()
            .code(HttpStatus.OK.value())
            .message("itinerary get 성공")
            .result(itineraryGetDeleteService.getItineraryByTripId(tripId))
            .build();

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
