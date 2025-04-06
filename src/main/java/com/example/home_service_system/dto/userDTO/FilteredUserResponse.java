package com.example.home_service_system.dto.userDTO;

import com.example.home_service_system.entity.enums.UserType;
import org.springframework.data.domain.Page;

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
    public static FilteredUserResponse from(Page<UserResponse> page,
                                            String sortBy,
                                            String sortDirection,
                                            LocalDateTime createdAtFrom,
                                            LocalDateTime createdAtTo,
                                            Long minBalance,
                                            Long maxBalance,
                                            UserType userType,
                                            String expertStatus,
                                            String customerStatus) {
        return new FilteredUserResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                sortBy,
                sortDirection,
                createdAtFrom,
                createdAtTo,
                minBalance,
                maxBalance,
                userType,
                expertStatus,
                customerStatus
        );
    }
}