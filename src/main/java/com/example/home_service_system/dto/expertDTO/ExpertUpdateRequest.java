package com.example.home_service_system.dto.expertDTO;

import java.time.LocalDate;

public record ExpertUpdateRequest(Long id,
                                  String firstName,
                                  String lastName,
                                  String username,
                                  String password,
                                  String nationalId,
                                  String phoneNumber,
                                  LocalDate birthday,
                                  String email,
                                  byte[] expertImage) {
}
