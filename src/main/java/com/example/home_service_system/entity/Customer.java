package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
/*@SQLDelete(sql = "UPDATE Customer SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")*/
public class Customer extends BaseEntity<Long> {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    User user;

    @OneToMany(mappedBy = "customer")
    List<Order> orderList = new ArrayList<>();

    /*@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus;*/

    @Column(nullable = false)
    Long balance = 0L;
}
