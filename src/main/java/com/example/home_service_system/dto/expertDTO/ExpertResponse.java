package com.example.home_service_system.dto.expertDTO;

import java.time.LocalDate;

public record ExpertResponse (String firstName,
                              String lastName,
                              String username,
                              String nationalId,
                              String phoneNumber,
                              LocalDate birthday,
                              String email,
                              byte[] expertImage){
}
