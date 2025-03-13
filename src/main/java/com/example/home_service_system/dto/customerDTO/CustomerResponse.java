package com.example.home_service_system.dto.customerDTO;

import com.example.home_service_system.entity.enums.UserStatus;

import java.time.LocalDate;

public record CustomerResponse(String firstName,
                               String lastName,
                               String username,
                               String nationalId,
                               String phoneNumber,
                               LocalDate birthday,
                               String email,
                               UserStatus userStatus,
                               Long balance) {
}
