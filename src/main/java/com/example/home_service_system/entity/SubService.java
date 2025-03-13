package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SubService extends BaseEntity<Long> {

    @Column(length = 50, nullable = false, unique = true)
    String subServiceName;

    @Column(nullable = false)
    Long subServiceBaseCost;

    @Column(length = 500, nullable = false)
    String subServiceDescription;

    @OneToMany(mappedBy = "subService")
    List<Order> orderList;
}
