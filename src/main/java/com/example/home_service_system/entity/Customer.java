package com.example.home_service_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    List<CustomerCommentAndRate> customerCommentAndRateList = new ArrayList<>();

    @Column(nullable = false)
    boolean isDeleted = false;
}
