package com.example.home_service_system.dto.mainServiceDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MainServiceUpdateRequest(
        @NotNull(message = "Id can not be null for update!")
        Long id,

        @NotBlank(message = "Main Service name cannot be null or empty!")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Main service name must contain only alphabetic characters!")
        @Size(min = 5, max = 25, message = "Main service name must be between 5 and 25 characters!")
        String name

        //List<Long> subServiceIds
) {
}
