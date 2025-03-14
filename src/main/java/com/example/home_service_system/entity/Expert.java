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

/*    @Min(value = 0, message = "Rating must be at least 0!")
    @Max(value = 100, message = "Rating must not exceed 100!")*/
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

    @Column(nullable = false)
    boolean isDeleted = false;
}
