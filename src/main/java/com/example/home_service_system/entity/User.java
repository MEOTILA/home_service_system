package com.example.home_service_system.entity;

import com.example.home_service_system.base.BaseEntity;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class User extends BaseEntity<Long> {

    @NotBlank(message = "Firstname can not be null or empty!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Firstname must contain only alphabetic characters!")
    @Size(min = 3, max = 25, message = "First name must be between 3 and 25 characters!")
    @Column(length = 25, nullable = false)
    String firstName;

    @NotBlank(message = "LastName can not be null or empty!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "LastName must contain only alphabetic characters!")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters!")
    @Column(length = 50, nullable = false)
    String lastName;

    @NotBlank(message = "Username can not be null or empty!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters!")
    @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters!")
    @Column(length = 25, nullable = false, unique = true)
    String username;

    @NotBlank(message = "Password can not be null or empty!")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least one uppercase letter, " +
                    "one digit, one special character, and be at least 8 characters long!"
    )
    @Size(min = 8, max = 250)
    @Column(length = 250, nullable = false)
    String password;

    @NotBlank(message = "National Code can not be null or empty!")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "National Code must contain only digits!"
    )
    @Size(min = 10, max = 10, message = "National ID must be 10 digits!")
    @Column(length = 10, nullable = false, unique = true)
    String nationalID;

    @NotBlank(message = "Phone Number can not be null or empty!")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Phone Number must contain only digits!"
    )
    @Size(min = 11, max = 11, message = "Phone number must be 11 digits!")
    @Column(length = 11, nullable = false, unique = true)
    String phoneNumber;

    @NotNull(message = "Birthday cannot be blank or empty!")
    @Past(message = "Birthday must be a past date!")
    @Column(nullable = false)
    LocalDate birthday;

    @NotBlank(message = "Email can not be null or empty!")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email format!")
    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    UserStatus userStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Override
    public String toString() {
        return "\n==============================" +
                "\nUser Details:" +
                "\nID: " + getId() +
                "\nFirst Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nUsername: " + username +
                "\nNational ID: " + nationalID +
                "\nPhone Number: " + phoneNumber +
                "\nBirthday: " + birthday +
                "\nEmail: " + email;
    }
}
