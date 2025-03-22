package com.example.home_service_system.dto.mainServiceDTO;

import com.example.home_service_system.entity.SubService;

import java.time.LocalDateTime;
import java.util.List;

public record MainServiceResponse(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Long> subServiceIds) {
}
