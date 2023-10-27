package com.fc.toy_project2.domain.itinerary.repository;

import com.fc.toy_project2.domain.itinerary.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
