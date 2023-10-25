package com.fc.toy_project2.domain.trip.entity;

import com.fc.toy_project2.domain.itinerary.entity.Itinerary;
import com.fc.toy_project2.domain.trip.dto.request.UpdateTripRequestDTO;
import com.fc.toy_project2.domain.trip.dto.response.TripResponseDTO;
import com.fc.toy_project2.global.util.DateTypeFormatterUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

/**
 * Trip Entity
 */
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

    /**
     * Builder 패턴이 적용된 Trip Entity 생성자
     *
     * @param id          여행 ID
     * @param name        여행 이름
     * @param startDate   여행 시작일
     * @param endDate     여행 종료일
     * @param itineraries 해당 여행의 여정 리스트
     */
    @Builder
    public Trip(Long id, String name, LocalDate startDate, LocalDate endDate, Boolean isDomestic,
        List<Itinerary> itineraries) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDomestic = isDomestic;
        this.itineraries = itineraries;
    }

    /**
     * Trip Entity를 TripResponseDTO로 변환
     *
     * @return TripResponseDTO
     */
    public TripResponseDTO toTripResponseDTO() {
        return TripResponseDTO.builder().id(this.id).name(this.name)
            .startDate(localDateToString(this.startDate)).endDate(localDateToString(this.endDate))
            .isDomestic(this.isDomestic).build();
    }

    /**
     * 여행 정보를 수정
     *
     * @param updateTripRequestDTO 여행 정보 수정 요청 DTO
     */
    public void updateTrip(UpdateTripRequestDTO updateTripRequestDTO) {
        this.name = updateTripRequestDTO.getName();
        this.startDate = DateTypeFormatterUtil.dateFormatter(updateTripRequestDTO.getStartDate());
        this.endDate = DateTypeFormatterUtil.dateFormatter(updateTripRequestDTO.getEndDate());
        this.isDomestic = updateTripRequestDTO.getIsDomestic();
    }

    /**
     * LocalDate 타입을 String 타입으로 변환
     *
     * @param date LocalDate 타입의 날짜
     * @return String 타입의 날짜
     */
    private String localDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
