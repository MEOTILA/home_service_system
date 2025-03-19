package com.example.home_service_system.dto.orderDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record OrderSaveRequest(
        @NotNull(message = "Sub-service ID cannot be null!")
        Long subServiceId,

        @NotNull(message = "Customer ID cannot be null!")
        Long customerId,

        @NotNull(message = "Offered cost cannot be null!")
        @Min(value = 0, message = "Offered cost cannot be negative!")
        Long customerOfferedCost,

        @NotBlank(message = "Description cannot be empty!")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters!")
        String customerDescription,

        @NotNull(message = "Service date cannot be null!")
        @Future(message = "Service date must be a future date!")
        LocalDateTime serviceDate,

        @NotBlank(message = "Address cannot be empty!")
        @Size(min = 5, max = 250, message = "Address must be between 5 and 250 characters!")
        String address
) {
}
