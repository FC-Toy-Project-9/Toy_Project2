package com.fc.toy_project2.domain.trip.entity;

import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Comment("국내 = 1, 국외 = 0")
    private Boolean isDomestic;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Itinerary> itineraries = new ArrayList<>();
}
