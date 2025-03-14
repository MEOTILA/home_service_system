package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CustomerCommentAndRate extends BaseEntity<Long> {
    @Column(nullable = false)
    @OneToOne
    Order order;

    @Column(nullable = false)
    @ManyToOne
    Customer customer;

    @Column(nullable = false)
    @ManyToOne
    Expert expert;

    @Column(length = 500)
    String customerComment;

    @Column(nullable = false)
    Integer customerRatingForExpert;

    @Column(nullable = false)
    boolean isDeleted = false;
}
