package com.example.home_service_system.dto.userDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String nationalId,
        String phoneNumber,
        LocalDate birthday,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
