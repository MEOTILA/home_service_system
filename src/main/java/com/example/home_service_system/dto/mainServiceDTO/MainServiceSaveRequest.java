package com.example.home_service_system.dto.mainServiceDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MainServiceSaveRequest(
        @NotBlank(message = "Main service name can not be null or empty!")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Main service name must contain only alphabetic characters!")
        @Size(min = 5, max = 25, message = "Main service name must be between 5 and 25 characters!")
        String name) {
}
