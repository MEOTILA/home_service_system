package com.example.home_service_system.dto.customerDTO;

import java.time.LocalDate;

public record CustomerResponse(String firstName,
                               String lastName,
                               String username,
                               String nationalId,
                               String phoneNumber,
                               LocalDate birthday,
                               String email) {
}
