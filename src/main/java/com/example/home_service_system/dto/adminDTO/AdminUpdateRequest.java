package com.example.home_service_system.dto.adminDTO;

import java.time.LocalDate;

public record AdminUpdateRequest(Long id,
                                 String firstName,
                                 String lastName,
                                 String username,
                                 String password,
                                 String nationalId,
                                 String phoneNumber,
                                 LocalDate birthday,
                                 String email) {
}
