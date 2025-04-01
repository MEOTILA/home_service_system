package com.example.home_service_system.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponseDTO {
    private String message;
    private String errorType;
    private LocalDateTime timestamp;
}

