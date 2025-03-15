package com.example.home_service_system.entity;

import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.persistence.*;
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
    List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    List<CustomerCommentAndRate> customerCommentAndRateList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus;

    @Column(nullable = false)
    Long balance = 0L;

    @Column(nullable = false)
    boolean isDeleted = false;
}
