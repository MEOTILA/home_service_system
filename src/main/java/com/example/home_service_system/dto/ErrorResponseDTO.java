package com.example.home_service_system.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponseDTO {
    private String message;
    private int statusCode;
}
