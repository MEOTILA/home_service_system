package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import com.example.home_service_system.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Column(length = 25, nullable = false)
    String firstName;

    @Column(length = 50, nullable = false)
    String lastName;

    @Column(length = 25, nullable = false, unique = true)
    String username;

    @Column(length = 250, nullable = false)
    String password;

    @Column(length = 10, nullable = false, unique = true)
    String nationalId;

    @Column(length = 11, nullable = false, unique = true)
    String phoneNumber;

    @Column(nullable = false)
    LocalDate birthday;

    @Column(length = 50, nullable = false, unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserType userType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    boolean isDeleted = false;
}
