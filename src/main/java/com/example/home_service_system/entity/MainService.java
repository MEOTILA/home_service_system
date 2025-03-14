package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class MainService extends BaseEntity<Long> {

    @Column(length = 50, nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    @OneToMany(mappedBy = "mainService")
    List<SubService> subServices = new ArrayList<>();

    @Column(nullable = false)
    boolean isDeleted = false;
}
