package com.example.home_service_system.dto.expertSuggestionDTO;

import com.example.home_service_system.entity.Order;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDateTime;

public record ExpertSuggestionUpdateRequest(
        @NotNull(message = "ID cannot be null")
        Long id,

        /*@NotNull(message = "Order ID cannot be null")
        Long orderId,*/

        @Size(max = 500, message = "Expert suggestion must be at most 500 characters")
        String expertSuggestion,

        @Positive(message = "Offered cost must be positive")
        Long expertOfferedCost,

        Duration serviceTimeDuration,

        @Future(message = "Service start time must be in the future")
        LocalDateTime expertServiceStartDateTime

) {
}
