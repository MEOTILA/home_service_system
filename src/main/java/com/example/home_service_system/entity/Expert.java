package com.example.home_service_system.entity;

import jakarta.persistence.*;
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
public class Expert extends User {

    @Column(nullable = false)
    byte[] expertImage;

    @Column(nullable = false)
    Integer rating = 0;

    @ManyToMany
    @JoinTable(
            name = "expert_subservice",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    List<SubService> expertServiceFields = new ArrayList<>();

    @OneToMany(mappedBy = "expert")
    List<CustomerCommentAndRate> customerCommentAndRateList = new ArrayList<>();
}
