package com.example.home_service_system.dto.adminDTO;

import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdminUpdateRequest(
        @NotNull(message = "Id can not be null for update!")
        Long id,
        String firstName,
        String lastName,
        String username,
        String password,
        String nationalId,
        String phoneNumber,
        LocalDate birthday,
        String email,
        LocalDateTime updatedAt) {
}
