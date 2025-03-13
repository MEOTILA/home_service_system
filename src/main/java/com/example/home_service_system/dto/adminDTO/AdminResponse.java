package com.example.home_service_system.dto.adminDTO;

import com.example.home_service_system.entity.Admin;

import java.time.LocalDate;

public record AdminResponse(Long id,
                            String firstName,
                            String lastName,
                            String username,
                            String nationalId,
                            String phoneNumber,
                            LocalDate birthday,
                            String email) {
}
