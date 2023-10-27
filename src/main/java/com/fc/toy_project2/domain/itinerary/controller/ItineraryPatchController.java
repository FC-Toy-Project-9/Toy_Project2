package com.fc.toy_project2.domain.itinerary.controller;

import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
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
@RequestMapping("/api/itinerary")
public class ItineraryPatchController {

    private final ItineraryPostUpdateService itinerarypostUpdateService;

    @PatchMapping("/accommodation/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<AccommodationResponseDTO>> updateAccommodation(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                                     @Valid @RequestBody ItineraryAccommodationCreateDTO itineraryAccommodationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.OK,
                itinerarypostUpdateService.Accommodation(itineraryAccommodationCreateDTO, tripId, itineraryId),
                "숙박 여정을 성공적으로 수정했습니다."));
    }

    @PatchMapping("/transportation/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<TransportationResponseDTO>> updateTransportation(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                                       @Valid @RequestBody ItineraryTransportationCreateDTO itineraryTransportationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.OK,
                itinerarypostUpdateService.Transportation(itineraryTransportationCreateDTO, tripId, itineraryId),
                "이동 여정을 성공적으로 수정했습니다."));
    }

    @PatchMapping("/visit/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<VisitResponseDTO>> updateVisit(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                     @Valid @RequestBody ItineraryVisitCreateDTO itineraryVisitCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.OK,
                itinerarypostUpdateService.Visit(itineraryVisitCreateDTO, tripId, itineraryId),
                "체류 여정을 성공적으로 수정했습니다."));
    }
}