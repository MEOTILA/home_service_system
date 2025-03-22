package com.example.home_service_system.dto.customerCommentAndRateDTO;

import com.example.home_service_system.entity.Order;

import java.time.LocalDateTime;

public record CustomerCommentAndRateResponse(
        Long id,
        Long orderId,
        Integer rating,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
