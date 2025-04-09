package com.example.home_service_system.dto.userDTO;

import com.example.home_service_system.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String nationalId;
    private String phoneNumber;
    private LocalDate birthday;
    private String email;
    private UserType userType;

    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;

    private Long minBalance;
    private Long maxBalance;

    private Long minOrderCount;
    private Long maxOrderCount;

    private Integer minExpertRating;
    private String expertStatus;

    private String customerStatus;

    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private String sortDirection = "ASC";
}
