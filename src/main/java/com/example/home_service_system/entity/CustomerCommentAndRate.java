package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comment_and_rate")
public class CustomerCommentAndRate extends BaseEntity<Long> {
    @OneToOne
    Order order;

    @Column(nullable = false)
    Integer rating;

    @Column(length = 500)
    String comment;

    @Column(nullable = false)
    boolean isDeleted = false;


    /* @ManyToOne
     Customer customer;

     @ManyToOne
     Expert expert;*/
}
