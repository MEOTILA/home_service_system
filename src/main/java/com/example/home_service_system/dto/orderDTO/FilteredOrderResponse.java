package com.example.home_service_system.dto.orderDTO;

import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.entity.enums.PaymentType;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record FilteredOrderResponse(
        List<OrderResponse> content,
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPages,
        String sortBy,
        String sortDirection,
        // Filter metadata
        Long subServiceId,
        Long customerId,
        Long expertId,
        Long minCost,
        Long maxCost,
        String description,
        LocalDateTime serviceStartDate,
        LocalDateTime serviceEndDate,
        String address,
        OrderStatus status,
        PaymentType paymentType,
        LocalDateTime createdAfter,
        LocalDateTime createdBefore,
        Boolean hasComment
) {
    /*public static FilteredOrderResponse from(Page<OrderResponse> page, OrderFilterDTO filter) {
        return new FilteredOrderResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                filter.getSortBy(),
                filter.getSortDirection(),
                filter.getSubServiceId(),
                filter.getCustomerId(),
                filter.getExpertId(),
                filter.getMinCost(),
                filter.getMaxCost(),
                filter.getDescription(),
                filter.getServiceStartDate(),
                filter.getServiceEndDate(),
                filter.getAddress(),
                filter.getStatus(),
                filter.getPaymentType(),
                filter.getCreatedAfter(),
                filter.getCreatedBefore(),
                filter.getHasComment()
        );
    }*/
}