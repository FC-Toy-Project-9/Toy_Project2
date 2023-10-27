//package com.fc.toy_project2.domain.itinerary.entity;
//
//import com.fc.toy_project2.domain.itinerary.dto.response.VisitResponseDTO;
//import jakarta.persistence.Entity;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//import java.time.LocalDateTime;
//
//
//@SuperBuilder
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class Visit extends Itinerary {
//
//    private String placeName;
//    private String placeRoadAddressName;
//    private LocalDateTime arrivalTime;
//    private LocalDateTime departureTime;
//
//    public VisitResponseDTO toVisitResponseDTO(){
//        return VisitResponseDTO.builder()
//                .placeName(this.placeName)
//                .placeRoadAddressName(this.placeRoadAddressName)
//                .departureTime(this.departureTime)
//                .arrivalTime(this.arrivalTime)
//                .build();
//    }
//}
