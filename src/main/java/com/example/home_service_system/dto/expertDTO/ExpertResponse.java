package com.example.home_service_system.dto.expertDTO;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ExpertResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String nationalId,
        String phoneNumber,
        LocalDate birthday,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        byte[] expertImage,
        Integer rating,
        UserStatus userStatus,
        Long balance,
        List<Order> orderList,
        List<SubService> expertServiceFields,
        List<CustomerCommentAndRate> customerCommentAndRateList) {
}
