package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {

    @ManyToOne
    SubService subService;

    @ManyToOne
    Customer customer;

    @ManyToOne
    Expert expert;

    @Column(nullable = false)
    Long customerOfferedCost;

    @Column(length = 500, nullable = false)
    String customerDescription;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentType paymentType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;

    @Column(nullable = false)
    boolean isDeleted = false;
}
