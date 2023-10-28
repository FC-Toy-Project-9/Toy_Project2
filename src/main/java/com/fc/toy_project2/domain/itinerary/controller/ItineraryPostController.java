package com.fc.toy_project2.domain.itinerary.controller;

import com.fc.toy_project2.domain.itinerary.dto.request.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.TransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.service.ItineraryPostUpdateService;
import com.fc.toy_project2.global.DTO.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/itineraries")
public class ItineraryPostController {

    private final ItineraryPostUpdateService itinerarypostUpdateService;


    @PostMapping("/accommodation/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<AccommodationResponseDTO>> createAccommodation(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                                     @Valid @RequestBody ItineraryAccommodationCreateDTO itineraryAccommodationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.CREATED,
                itinerarypostUpdateService.createdAccommodation(itineraryAccommodationCreateDTO, tripId, itineraryId),
                "숙박 여정을 성공적으로 등록했습니다."));
    }

    @PostMapping("/transportation/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<TransportationResponseDTO>> createTransportation(@PathVariable Long tripId,@PathVariable Long itineraryId,
                                                                                       @Valid @RequestBody ItineraryTransportationCreateDTO itineraryTransportationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.CREATED,
                itinerarypostUpdateService.createdTransportation(itineraryTransportationCreateDTO, tripId, itineraryId),
                "이동 여정을 성공적으로 등록했습니다."));
    }

    @PostMapping("/visit/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<VisitResponseDTO>> createVisit(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                     @Valid @RequestBody ItineraryVisitCreateDTO itineraryVisitCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.CREATED,
                itinerarypostUpdateService.createdVisit(itineraryVisitCreateDTO, tripId, itineraryId),
                "체류 여정을 성공적으로 등록했습니다."));
    }
}