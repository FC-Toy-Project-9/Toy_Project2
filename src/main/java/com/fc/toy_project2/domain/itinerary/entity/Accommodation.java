package com.fc.toy_project2.domain.itinerary.entity;

import com.fc.toy_project2.domain.itinerary.dto.response.AccommodationResponseDTO;
import com.fc.toy_project2.domain.trip.entity.Trip;
import com.fc.toy_project2.global.util.DateTypeFormatterUtil;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@Entity
public class Accommodation extends Itinerary{

    private String accommodationName;

    private String accommodationRoadAddressName;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @Builder
    public Accommodation(Long id, Trip trip, String itineraryName, String accommodationName, String accommodationRoadAddressName,
        LocalDateTime checkIn, LocalDateTime checkOut) {
        super(id, trip, itineraryName);
        this.accommodationName = accommodationName;
        this.accommodationRoadAddressName = accommodationRoadAddressName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public AccommodationResponseDTO toAccommodationResponseDTO() {
        return AccommodationResponseDTO.builder().itineraryId(super.getId())
            .itineraryName(super.getItineraryName()).accommodationName(this.accommodationName)
            .accommodationRoadAddressName(this.accommodationRoadAddressName)
            .checkIn(DateTypeFormatterUtil.localDateTimeToString(this.checkIn))
            .checkOut(DateTypeFormatterUtil.localDateTimeToString(this.checkOut)).build();
    }

    public void updateAccommodationInfo(String itineraryName, String accommodationName,
        String accommodationRoadAddressName, LocalDateTime checkIn, LocalDateTime checkOut) {
        super.updateItineraryName(itineraryName);
        this.accommodationName = accommodationName;
        this.accommodationRoadAddressName = accommodationRoadAddressName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
