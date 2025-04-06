package com.example.home_service_system.dto.userDTO;

import com.example.home_service_system.entity.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;

public record FilteredUserResponse(
        List<UserResponse> content,
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPages,
        String sortBy,
        String sortDirection,

        LocalDateTime createdAtFrom,
        LocalDateTime createdAtTo,
        Long minBalance,
        Long maxBalance,
        UserType userType,
        String expertStatus,
        String customerStatus
) {
}