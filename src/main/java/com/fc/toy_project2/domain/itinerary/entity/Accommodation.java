//package com.fc.toy_project2.domain.itinerary.entity;
//
//import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
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
//public class Accommodation extends Itinerary {
//
//    private String accommodationName;
//    private LocalDateTime checkIn;
//    private LocalDateTime checkOut;
//    private String accommodationRoadAddressName;
//
//    public AccommodationResponseDTO toAccommodationResponseDTO(){
//        return AccommodationResponseDTO.builder()
//                .accommodationName(this.accommodationName)
//                .accommodationRoadAddressName(this.accommodationRoadAddressName)
//                .checkIn(this.checkIn)
//                .checkOut(this.checkOut)
//                .build();
//    }
//}