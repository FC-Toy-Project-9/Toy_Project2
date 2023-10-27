package com.fc.toy_project2.domain.itinerary.repository;

import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    List<Itinerary> findAllByTripId(Long tripId);
    Itinerary findByTripIdAndId(Long tripId, Long id);
}