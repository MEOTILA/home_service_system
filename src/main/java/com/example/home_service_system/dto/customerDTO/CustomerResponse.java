package com.example.home_service_system.dto.customerDTO;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String nationalId,
        String phoneNumber,
        LocalDate birthday,
        String email,
        List<Long> orderIds,
        //List<Long> customerCommentAndRateIds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserStatus userStatus,
        Long balance) {
}
