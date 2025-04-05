package com.example.home_service_system.dto.orderDTO;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.entity.enums.PaymentType;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long subServiceId,
        Long customerId,
        Long expertId,
        Long customerOfferedCost,
        String customerDescription,
        PaymentType paymentType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime serviceDate,
        String address,
        OrderStatus status,
        List<Long> expertSuggestionListIds,
        Long customerCommentAndRateId
) {
}
