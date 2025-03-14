package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CustomerCommentAndRate extends BaseEntity<Long> {
    @OneToOne
    Order order;

    @ManyToOne
    Customer customer;

    @ManyToOne
    Expert expert;

    @Column(length = 500)
    String comment;

    @Column(nullable = false)
    Integer rating;

    @Column(nullable = false)
    boolean isDeleted = false;
}
