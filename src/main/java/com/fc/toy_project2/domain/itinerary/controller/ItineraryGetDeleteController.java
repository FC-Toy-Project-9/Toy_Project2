package com.fc.toy_project2.domain.itinerary.controller;


import com.fc.toy_project2.domain.itinerary.dto.ResultResponse;
import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/itinerary")
public class ItineraryGetDeleteController {

    private final ItineraryGetDeleteService itineraryGetDeleteService;

    @GetMapping("/get")
    public ResponseEntity<ResultResponse> getItineraryByTripId(@RequestParam long tripId) {
        ResultResponse res = ResultResponse.builder()
            .code(HttpStatus.OK.value())
            .message("itinerary get 성공")
            .result(itineraryGetDeleteService.getItineraryByTripId(tripId))
            .build();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResultResponse> deleteItineray(@RequestParam long itineraryId)
        throws NotFoundException {
        ResultResponse res = ResultResponse.builder()
            .code(HttpStatus.OK.value())
            .message("itinerary delete 성공")
            .result(itineraryGetDeleteService.deleteItinerary(itineraryId))
            .build();

        return new ResponseEntity<>(res, HttpStatus.OK);


    }

}
