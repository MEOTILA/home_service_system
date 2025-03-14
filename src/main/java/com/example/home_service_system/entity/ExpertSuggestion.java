package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ExpertSuggestion extends BaseEntity<Long> {

    @ManyToOne
    Order order;

    @Column(length = 500, nullable = false)
    String expertSuggestion;

    @Column(nullable = false)
    LocalDateTime suggestionSubmitDate;

    @Column(nullable = false)
    Long expertOfferedCost;

    @Column(nullable = false)
    Time serviceTimeDuration;

    @Column(nullable = false)
    LocalDateTime expertServiceStartDate;
}
