package com.fc.toy_project2.domain.itinerary.controller;

import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryAccommodationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryTransportationCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.createDTO.ItineraryVisitCreateDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.patchDTO.ItineraryAccommodationPatchDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.patchDTO.ItineraryVisitPatchDTO;
import com.fc.toy_project2.domain.itinerary.dto.request.patchDTO.ItineraryTransportationPatchDTO;
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
public class ItineraryPatchController {

    private final ItineraryPostUpdateService itinerarypostUpdateService;

    @PatchMapping("/accommodation/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<ItineraryAccommodationResponseDTO>> updateAccommodation(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                                              @Valid @RequestBody ItineraryAccommodationPatchDTO ItineraryAccommodationPatchDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.OK,
                itinerarypostUpdateService.patchAccommodation(ItineraryAccommodationPatchDTO, tripId, itineraryId),
                "숙박 여정을 성공적으로 수정했습니다."));
    }

    @PatchMapping("/transportation/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<ItineraryTransportationResponseDTO>> updateTransportation(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                                                @Valid @RequestBody ItineraryTransportationPatchDTO itineraryTransportationPatchDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.OK,
                itinerarypostUpdateService.patchTransportation(itineraryTransportationPatchDTO, tripId, itineraryId),
                "이동 여정을 성공적으로 수정했습니다."));
    }

    @PatchMapping("/visit/{tripId}/{itineraryId}")
    public ResponseEntity<ResponseDTO<ItineraryVisitResponseDTO>> updateVisit(@PathVariable Long tripId, @PathVariable Long itineraryId,
                                                                              @Valid @RequestBody ItineraryVisitPatchDTO itineraryVisitPatchDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.res(HttpStatus.OK,
                itinerarypostUpdateService.patchVisit(itineraryVisitPatchDTO, tripId, itineraryId),
                "체류 여정을 성공적으로 수정했습니다."));
    }
}