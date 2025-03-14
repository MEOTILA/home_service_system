package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CustomerCommentAndRate extends BaseEntity<Long> {

    @Column(nullable = false)
    @ManyToOne
    Customer customer;

    @Column(nullable = false)
    @ManyToOne
    Expert expert;

    @Column(length = 500)
    String customerComment;

    @Column(nullable = false)
    @Min(value = 0, message = "Rating must be at least 0!")
    @Max(value = 100, message = "Rating must not exceed 100!")
    Integer customerRatingForExpert;
}
