package com.fc.toy_project2.domain.trip.controller;

import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.service.TripService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTrips() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "성공적으로 여행 정보 목록을 조회했습니다.");
        response.put("data", tripService.getTrips());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Map<String, Object>> getTripById(@PathVariable long tripId) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "성공적으로 여행 정보를 조회했습니다.");
        response.put("data", tripService.getTripById(tripId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> updateTrip(
        @Valid @RequestBody UpdateTripRequestDTO updateTripRequestDTO) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "성공적으로 여행 정보를 수정했습니다.");
        response.put("data", tripService.updateTrip(updateTripRequestDTO));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
