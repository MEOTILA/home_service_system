package com.example.home_service_system.dto.customerCommentAndRateDTO;

import com.example.home_service_system.entity.Order;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerCommentAndRateUpdateRequest(
        @NotNull(message = "Id cannot be null for update!")
        Long id,

        /*@NotNull(message = "Order ID cannot be null!")
        Long orderId,*/

        @Min(value = 0, message = "Rating must be at least 0!")
        @Max(value = 100, message = "Rating must be at most 100!")
        Integer rating,

        @Size(max = 500, message = "Comment must be less than 500 characters!")
        String comment

) {
}
