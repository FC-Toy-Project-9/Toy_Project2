//package com.fc.toy_project2.domain.itinerary.controller;
//
//
//import com.fc.toy_project2.domain.itinerary.dto.response.GetResponseDTO;
//import com.fc.toy_project2.domain.itinerary.dto.response.SearchResponseDTO;
//import com.fc.toy_project2.domain.itinerary.service.ItineraryGetDeleteService;
//import com.fc.toy_project2.global.DTO.ResponseDTO;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/itinerary")
//public class ItineraryGetDeleteController {
//
//    private final ItineraryGetDeleteService itineraryGetDeleteService;
//
//    @GetMapping("/keyword/{query}")
//    public ResponseEntity<ResponseDTO<List<SearchResponseDTO>>> getPlaceByKeyword(
//        @PathVariable String query)
//        throws Exception {
//        return ResponseEntity.status(HttpStatus.OK).body(
//            ResponseDTO.res(HttpStatus.OK, itineraryGetDeleteService.getPlaceByKeyword(query),
//                "성공적으로 키워드로 장소를 조회했습니다."));
//
//    }
//
//    @GetMapping("/{tripId}")
//    public ResponseEntity<ResponseDTO<List>> getItineraryByTripId(@PathVariable long tripId) {
//        return ResponseEntity.status(HttpStatus.OK).body(
//            ResponseDTO.res(HttpStatus.OK, itineraryGetDeleteService.getItineraryByTripId(tripId),
//                "성공적으로 여정을 조회했습니다."));
//    }
//
//    @DeleteMapping("/{itineraryId}")
//    public ResponseEntity<ResponseDTO<GetResponseDTO>> deleteItinerary(
//        @PathVariable long itineraryId)
//        throws NotFoundException {
//        return ResponseEntity.status(HttpStatus.OK).body(
//            ResponseDTO.res(HttpStatus.OK, itineraryGetDeleteService.deleteItinerary(itineraryId),
//                "성공적으로 여정을 삭제했습니다."));
//    }
//
//}
