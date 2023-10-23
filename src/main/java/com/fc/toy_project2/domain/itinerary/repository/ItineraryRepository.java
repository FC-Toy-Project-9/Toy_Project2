package com.fc.toy_project2.domain.itinerary.repository;

import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

}
