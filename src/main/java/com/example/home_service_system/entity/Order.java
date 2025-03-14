package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import com.example.home_service_system.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Order extends BaseEntity<Long> {

    @ManyToOne
    @Column(nullable = false)
    SubService subService;

    @Column(nullable = false)
    Long customerOfferedCost;

    @Column(length = 500, nullable = false)
    String customerDescription;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime submitDate;

    @Column(nullable = false)
    LocalDateTime serviceDate;

    @Column(nullable = false, length = 250)
    String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    @OneToMany(mappedBy = "order")
    List<ExpertSuggestion> expertSuggestionList = new ArrayList<>();

    @OneToOne
    CustomerCommentAndRate customerCommentAndRate;

    @Column(nullable = false)
    boolean isDeleted = false;
}
