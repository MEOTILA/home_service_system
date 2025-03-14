package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
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

    @Column(nullable = false)
    @ManyToOne
    MainService mainService;

    @OneToMany(mappedBy = "subService")
    List<Order> orderList = new ArrayList<>();

    @ManyToMany
    List<Expert> expertList = new ArrayList<>();

    @Column(nullable = false)
    boolean isDeleted = false;
}
