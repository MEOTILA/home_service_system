package com.example.home_service_system.dto.orderDTO;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long subServiceId,
        Long customerId,
        Long expertId,
        Long customerOfferedCost,
        String customerDescription,
        LocalDateTime submitDate,
        LocalDateTime serviceDate,
        String address,
        OrderStatus status,
        List<Long> expertSuggestionListIds,
        Long customerCommentAndRateId
) {
}
