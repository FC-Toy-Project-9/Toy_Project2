package com.fc.toy_project2.domain.trip.controller;

import com.fc.toy_project2.domain.trip.dto.request.PostTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.response.TripResponseDTO;
import com.fc.toy_project2.domain.trip.service.TripService;
import com.fc.toy_project2.global.DTO.ResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trip REST Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class TripRestController {

    private final TripService tripService;

    @PostMapping
    public ResponseDTO postTrip(@Valid @RequestBody PostTripRequestDTO postTripRequestDTO){
        tripService.postTrip(postTripRequestDTO);
        return ResponseDTO.res(HttpStatus.CREATED, "성공적으로 여행을 등록했습니다.");
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<TripResponseDTO>>> getTrips() {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.res(HttpStatus.OK, tripService.getTrips(), "성공적으로 여행 정보 목록을 조회했습니다."));
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<ResponseDTO<TripResponseDTO>> getTripById(@PathVariable long tripId) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.res(HttpStatus.OK, tripService.getTripById(tripId),
                "성공적으로 여행 정보를 조회했습니다."));
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<TripResponseDTO>> updateTrip(
        @Valid @RequestBody UpdateTripRequestDTO updateTripRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.res(HttpStatus.OK, tripService.updateTrip(updateTripRequestDTO),
                "성공적으로 여행 정보를 수정했습니다."));
    }
}
