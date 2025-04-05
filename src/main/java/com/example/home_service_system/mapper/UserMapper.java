package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.User;

public class UserMapper {

    public static UserResponse to(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getNationalId(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
