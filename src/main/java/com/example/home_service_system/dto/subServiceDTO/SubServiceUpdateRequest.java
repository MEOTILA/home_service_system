package com.example.home_service_system.dto.subServiceDTO;

import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.MainService;
import jakarta.validation.constraints.*;

import java.util.List;

public record SubServiceUpdateRequest(
        @NotNull(message = "Id cannot be null for update!")
        Long id,

        @NotBlank(message = "Sub service name cannot be null or empty!")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Sub service name must contain only alphabetic characters!")
        @Size(min = 5, max = 25, message = "Sub service name must be between 5 and 25 characters!")
        String name,

        @NotNull(message = "Base cost cannot be null!")
        @Min(value = 0, message = "Base cost cannot be negative!")
        Long baseCost,

        @NotBlank(message = "Description cannot be empty!")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters!")
        String description,

        @NotNull(message = "Main service ID cannot be null!")
        Long mainServiceId

        //List<Long> expertIds
) {
}
