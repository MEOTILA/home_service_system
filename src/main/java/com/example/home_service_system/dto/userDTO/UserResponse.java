package com.example.home_service_system.dto.userDTO;

import com.example.home_service_system.entity.enums.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String nationalId,
        String phoneNumber,
        LocalDate birthday,
        String email,
        UserType userType,
        String userStatus,
        Integer expertRating,
        Long balance,
        byte[] image,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long orderCount,
        boolean isDeleted
) {
    /*public String getImageBase64() {
        return image != null ? java.util.Base64.getEncoder().encodeToString(image) : null;
    }*/
}
