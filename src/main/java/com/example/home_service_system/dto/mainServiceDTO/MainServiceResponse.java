package com.example.home_service_system.dto.mainServiceDTO;

import com.example.home_service_system.entity.SubService;

import java.util.List;

public record MainServiceResponse(
        Long id,
        String name,
        List<Long> subServiceIds) {
}
