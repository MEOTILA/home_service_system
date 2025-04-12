package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.userDTO.FilteredUserResponse;
import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse to(User user) {
        if (user == null) return null;

        // Handle Expert/Customer specific fields
        String userStatus = null;
        Integer expertRating = null;
        Long balance = null;
        byte[] expertImage = null;
        Long orderCount = null;

        if (user.getExpert() != null) {
            userStatus = user.getUserStatus().name();
            expertRating = user.getExpert().getRating();
            balance = user.getExpert().getBalance();
            expertImage = user.getExpert().getExpertImage();
            orderCount = (long) user.getExpert().getOrderList().size();
        } else if (user.getCustomer() != null) {
            userStatus = user.getUserStatus().name();
            balance = user.getCustomer().getBalance();
            orderCount = (long) user.getCustomer().getOrderList().size();
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
                user.getUserType(),
                userStatus,
                expertRating,
                balance,
                expertImage,
                user.getCreatedAt(),
                user.getUpdatedAt(),
                orderCount,
                user.isDeleted()
        );
    }

    public static FilteredUserResponse toFilteredResponse(
            Page<User> userPage,
            String sortBy,
            String sortDirection,
            LocalDateTime createdAtFrom,
            LocalDateTime createdAtTo,
            Long minBalance,
            Long maxBalance,
            UserType userType,
            String expertStatus,
            String customerStatus) {

        // Convert Page<User> to Page<UserResponse>
        Page<UserResponse> responsePage = new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserMapper::to)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );

        return new FilteredUserResponse(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                sortBy,
                sortDirection,
                createdAtFrom,
                createdAtTo,
                minBalance,
                maxBalance,
                userType,
                expertStatus,
                customerStatus
        );
    }


    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::to)
                .collect(Collectors.toList());
    }
}
