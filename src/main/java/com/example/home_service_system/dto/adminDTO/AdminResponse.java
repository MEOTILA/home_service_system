package com.example.home_service_system.dto.adminDTO;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdminResponse(Long id,
                            String firstName,
                            String lastName,
                            String username,
                            String nationalId,
                            String phoneNumber,
                            LocalDate birthday,
                            String email,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
}
