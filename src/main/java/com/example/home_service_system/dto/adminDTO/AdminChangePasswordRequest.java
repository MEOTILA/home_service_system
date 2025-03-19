package com.example.home_service_system.dto.adminDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminChangePasswordRequest(
        @NotNull(message = "Id is required for change password!")
        Long id,

        @NotBlank(message = "Current password is required")
        String currentPassword,

        @NotBlank(message = "Password can not be null or empty!")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must contain at least one uppercase letter, " +
                        "one digit, one special character, and be at least 8 characters long!"
        )
        @Size(min = 8, max = 250)
        String newPassword) {

}

