package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
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
public class Expert extends BaseEntity<Long> {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    User user;

    @Lob
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

    @OneToMany(mappedBy = "expert")
    List<ExpertSuggestion> expertSuggestionList;

    @ManyToMany
    @JoinTable(
            name = "expert_subservice",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    List<SubService> expertServiceFields = new ArrayList<>();
}
