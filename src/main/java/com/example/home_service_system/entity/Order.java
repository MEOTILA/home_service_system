package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import com.example.home_service_system.entity.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

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

    @Column(nullable = false)
    LocalDateTime orderSubmitDate;

    @Column(nullable = false)
    LocalDateTime orderServiceDate;

    @Column(nullable = false, length = 250)
    String Address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    List<ExpertSuggestion> expertSuggestionList = new ArrayList<>();

    @OneToOne
    CustomerCommentAndRate customerCommentAndRate;

    @Column(nullable = false)
    boolean isDeleted = false;
}
