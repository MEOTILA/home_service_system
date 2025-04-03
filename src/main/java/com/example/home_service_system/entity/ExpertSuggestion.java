package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ExpertSuggestion extends BaseEntity<Long> {

    @ManyToOne
    Order order;

    @ManyToOne
    Expert expert;

    @Column(length = 500, nullable = false)
    String expertSuggestion;

    /*@CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime suggestionSubmitDate;*/

    @Column(nullable = false)
    Long expertOfferedCost;

    @Column(nullable = false)
    Duration serviceTimeDuration;

    @Column(nullable = false)
    LocalDateTime expertServiceStartDateTime;
}
