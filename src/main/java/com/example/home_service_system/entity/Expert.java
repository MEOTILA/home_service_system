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
public class Expert extends User {

    @Column(nullable = false)
    byte[] expertImage;

    @Column(nullable = false)
    Integer rating = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus;

    @Column(nullable = false)
    Long balance = 0L;

    @OneToMany(mappedBy = "expert")
    List<Order> orderList = new ArrayList<>();


    /*@JoinTable(
            name = "expert_subservice",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )*/
    @ManyToMany
    @JoinTable(
            name = "expert_subservice",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    List<SubService> expertServiceFields = new ArrayList<>();

    @OneToMany(mappedBy = "expert")
    List<CustomerCommentAndRate> customerCommentAndRateList = new ArrayList<>();

    @Column(nullable = false)
    boolean isDeleted = false;
}
