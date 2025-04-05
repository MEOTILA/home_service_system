package com.example.home_service_system.dto.orderDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderPaymentRequest(
        @NotNull(message = "Order ID can not be null for payment!")
        Long id,

        @NotNull(message = "Customer ID can not be null for payment!")
        Long customerId,

        @Size(min = 16, max = 16, message = "Card number must be 16 characters!")
        String cardNumber
) {
}
