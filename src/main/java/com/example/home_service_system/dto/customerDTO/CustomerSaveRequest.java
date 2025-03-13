package com.example.home_service_system.dto.customerDTO;

import java.time.LocalDate;

public record CustomerSaveRequest(String firstName,
                                  String lastName,
                                  String username,
                                  String password,
                                  String nationalId,
                                  String phoneNumber,
                                  LocalDate birthday,
                                  String email) {
}
