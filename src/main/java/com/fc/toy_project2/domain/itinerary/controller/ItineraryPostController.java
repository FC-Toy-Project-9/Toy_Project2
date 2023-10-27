package com.fc.toy_project2.domain.itinerary.controller;

import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryAccommodationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryTransportationResponseDTO;
import com.fc.toy_project2.domain.itinerary.dto.response.ItineraryVisitResponseDTO;
import com.fc.toy_project2.domain.itinerary.service.ItineraryPostUpdateService;
import com.fc.toy_project2.global.DTO.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/itinerary")
public class ItineraryPostController {

    private final ItineraryPostUpdateService itinerarypostUpdateService;


    @PostMapping("/accommodation/{tripId}")
    public ResponseEntity<ResponseDTO<ItineraryAccommodationResponseDTO>> createAccommodation(@PathVariable Long tripId,
                                                                                              @Valid @RequestBody ItineraryAccommodationCreateDTO itineraryAccommodationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.CREATED,
                itinerarypostUpdateService.createAccommodation(itineraryAccommodationCreateDTO, tripId),
                "숙박 여정을 성공적으로 등록했습니다."));
    }

    @PostMapping("/transportation/{tripId}")
    public ResponseEntity<ResponseDTO<ItineraryTransportationResponseDTO>> createTransportation(@PathVariable Long tripId,
                                                                                                @Valid @RequestBody ItineraryTransportationCreateDTO itineraryTransportationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.CREATED,
                itinerarypostUpdateService.createTransportation(itineraryTransportationCreateDTO, tripId),
                "이동 여정을 성공적으로 등록했습니다."));
    }

    @PostMapping("/visit/{tripId}")
    public ResponseEntity<ResponseDTO<ItineraryVisitResponseDTO>> createVisit(@PathVariable Long tripId,
                                                                              @Valid @RequestBody ItineraryVisitCreateDTO itineraryVisitCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.CREATED,
                itinerarypostUpdateService.createVisit(itineraryVisitCreateDTO, tripId),
                "체류 여정을 성공적으로 등록했습니다."));
    }
}