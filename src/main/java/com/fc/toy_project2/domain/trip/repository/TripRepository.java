package com.fc.toy_project2.domain.trip.repository;

import com.fc.toy_project2.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Long, Trip> {

}
