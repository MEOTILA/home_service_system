package com.example.home_service_system.dto.adminDTO;

import java.time.LocalDate;

public record AdminSaveRequest(String firstName,
                               String lastName,
                               String username,
                               String password,
                               String nationalId,
                               String phoneNumber,
                               LocalDate birthday,
                               String email) {
}
