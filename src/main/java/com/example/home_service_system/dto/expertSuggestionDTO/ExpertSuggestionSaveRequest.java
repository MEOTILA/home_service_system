package com.example.home_service_system.dto.expertSuggestionDTO;

import com.example.home_service_system.entity.Order;
import jakarta.validation.constraints.*;

import java.time.Duration;
import java.time.LocalDateTime;

public record ExpertSuggestionSaveRequest(

        @NotNull(message = "Order cannot be null")
        Order order,

        @NotBlank(message = "Expert suggestion cannot be empty")
        @Size(max = 500, message = "Expert suggestion must be at most 500 characters")
        String expertSuggestion,

        @NotNull(message = "Offered cost cannot be null")
        @Positive(message = "Offered cost must be positive")
        Long expertOfferedCost,

        @NotNull(message = "Service duration cannot be null")
        Duration serviceTimeDuration,

        @NotNull(message = "Service start time cannot be null")
        @Future(message = "Service start time must be in the future")
        LocalDateTime expertServiceStartDateTime
) {
}
