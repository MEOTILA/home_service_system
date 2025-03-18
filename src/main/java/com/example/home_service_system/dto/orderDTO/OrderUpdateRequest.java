package com.example.home_service_system.dto.orderDTO;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.ExpertSuggestion;
import com.example.home_service_system.entity.enums.OrderStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record OrderUpdateRequest(
        @NotNull(message = "Id can not be null for update!")
        Long id,

        Long subServiceId,

        Long customerId,

        Long expertId,

        @Min(value = 0, message = "Customer offered cost must be positive!")
        Long customerOfferedCost,

        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters!")
        String customerDescription,

        //@Future(message = "Service date must be in the future!")
        LocalDateTime serviceDate,

        @Size(min = 5, max = 250, message = "Address must be between 5 and 250 characters!")
        String address,

        OrderStatus status,

        List<Long> expertSuggestionListIds,

        CustomerCommentAndRate customerCommentAndRate
) {
}
