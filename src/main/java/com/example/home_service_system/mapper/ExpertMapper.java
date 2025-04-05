package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.entity.enums.UserType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpertMapper {
    public static ExpertResponse to(Expert expert) {
        if (expert == null || expert.getUser() == null) {
            return null;
        }

        User user = expert.getUser();
        return new ExpertResponse(
                expert.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getNationalId(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                expert.getExpertImage(),
                expert.getRating(),
                expert.getUserStatus(),
                expert.getBalance(),
                expert.getOrderList() != null
                        ? expert.getOrderList().stream()
                        .map(order -> order.getId()).collect(Collectors.toList())
                        : Collections.emptyList(),
                expert.getExpertSuggestionList() != null
                        ? expert.getExpertSuggestionList().stream()
                        .map(e -> e.getId()).collect(Collectors.toList())
                        : Collections.emptyList(),
                expert.getExpertServiceFields() != null
                        ? expert.getExpertServiceFields().stream()
                        .map(SubService::getId).collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }
    /*public static ExpertResponse to(Expert expert) {
        if (expert == null)
            return null;
        return new ExpertResponse(
                expert.getId(),
                expert.getUser().getFirstName(),
                expert.getUser().getLastName(),
                expert.getUser().getUsername(),
                expert.getUser().getNationalId(),
                expert.getUser().getPhoneNumber(),
                expert.getUser().getBirthday(),
                expert.getUser().getEmail(),
                expert.getUser().getCreatedAt(),
                expert.getUser().getUpdatedAt(),
                expert.getExpertImage(),
                expert.getRating(),
                expert.getUserStatus(),
                expert.getBalance(),
                expert.getOrderList() != null
                        ? expert.getOrderList().stream()
                        .map(order -> order.getId()).collect(Collectors.toList())
                        : Collections.emptyList(),
                expert.getExpertSuggestionList() != null
                        ? expert.getExpertSuggestionList().stream()
                        .map(e -> e.getId()).collect(Collectors.toList())
                        : Collections.emptyList(),
                expert.getExpertServiceFields() != null
                        ? expert.getExpertServiceFields().stream()
                        .map(SubService::getId).collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }*/

    public static Expert fromSaveRequest(ExpertSaveRequest request) {
        if (request == null) {
            return null;
        }

        // Create User first
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setNationalId(request.nationalId());
        user.setPhoneNumber(request.phoneNumber());
        user.setBirthday(request.birthday());
        user.setEmail(request.email());
        user.setUserType(UserType.EXPERT);

        // Create Expert and establish relationship
        Expert expert = new Expert();
        expert.setUser(user);

        // Set expert-specific fields
        try {
            expert.setExpertImage(request.expertImage().getBytes());
        } catch (IOException e) {
            throw new CustomApiException("Failed to process image",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        expert.setRating(0);
        expert.setUserStatus(UserStatus.NEW);
        expert.setBalance(0L);

        return expert;
    }
    /*public static Expert fromSaveRequest(ExpertSaveRequest request) {
        if (request == null)
            return null;
        Expert expert = new Expert();
        expert.getUser().setFirstName(request.firstName());
        expert.getUser().setLastName(request.lastName());
        expert.getUser().setUsername(request.username());
        expert.getUser().setPassword(request.password());
        expert.getUser().setNationalId(request.nationalId());
        expert.getUser().setPhoneNumber(request.phoneNumber());
        expert.getUser().setBirthday(request.birthday());
        expert.getUser().setEmail(request.email());
        //expert.setExpertImage(request.expertImage());
        try {
            expert.setExpertImage(request.expertImage().getBytes());
        } catch (IOException e) {
            throw new CustomApiException("Failed to process image",
                    CustomApiExceptionType.BAD_REQUEST);
        }

        expert.setRating(0);
        expert.setBalance(0L);
        return expert;
    }*/
    public static ExpertUpdateRequest toUpdateRequest(Expert expert) {
        if (expert == null || expert.getUser() == null) {
            return null;
        }

        User user = expert.getUser();
        List<Long> serviceFieldIds = expert.getExpertServiceFields() != null
                ? expert.getExpertServiceFields().stream()
                .map(SubService::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new ExpertUpdateRequest(
                expert.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getNationalId(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getEmail(),
                null,
                expert.getRating(),
                expert.getUserStatus(),
                expert.getBalance(),
                serviceFieldIds
        );
    }
    /*public static ExpertUpdateRequest toUpdateRequest(Expert expert) {
        if (expert == null)
            return null;
        return new ExpertUpdateRequest(
                expert.getId(),
                expert.getUser().getFirstName(),
                expert.getUser().getLastName(),
                expert.getUser().getUsername(),
                expert.getUser().getPassword(),
                expert.getUser().getNationalId(),
                expert.getUser().getPhoneNumber(),
                expert.getUser().getBirthday(),
                expert.getUser().getEmail(),
                //expert.getExpertImage(),
                null,
                expert.getRating(),
                expert.getUserStatus(),
                expert.getBalance(),
                *//*expert.getOrderList() != null
                        ? expert.getOrderList().stream()
                        .map(order -> order.getId()).collect(Collectors.toList())
                        : Collections.emptyList(),
                expert.getExpertSuggestionList() != null
                        ? expert.getExpertSuggestionList().stream()
                        .map(e -> e.getId()).collect(Collectors.toList())
                        : Collections.emptyList(),*//*
                expert.getExpertServiceFields() != null
                        ? expert.getExpertServiceFields().stream()
                        .map(SubService::getId).collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }*/

    /*public static Expert fromUpdateRequest(ExpertUpdateRequest request) {
        if (request == null)
            return null;
        Expert expert = new Expert();
        expert.setId(request.id());
        expert.setFirstName(request.firstName());
        expert.setLastName(request.lastName());
        expert.setUsername(request.username());
        expert.setPassword(request.password());
        expert.setNationalId(request.nationalId());
        expert.setPhoneNumber(request.phoneNumber());
        expert.setBirthday(request.birthday());
        expert.setEmail(request.email());
        expert.setExpertImage(request.expertImage());
        expert.setRating(request.rating());
        expert.setUserStatus(request.userStatus());
        expert.setBalance(request.balance());
        return expert;
    }*/

    /*public static Expert toExpertFromResponse(ExpertResponse response) {
        if (response == null)
            return null;
        Expert expert = new Expert();
        expert.setId(response.id());
        expert.setFirstName(response.firstName());
        expert.setLastName(response.lastName());
        expert.setUsername(response.username());
        expert.setNationalId(response.nationalId());
        expert.setPhoneNumber(response.phoneNumber());
        expert.setBirthday(response.birthday());
        expert.setEmail(response.email());
        expert.setCreatedAt(response.createdAt());
        expert.setUpdatedAt(response.updatedAt());
        expert.setExpertImage(response.expertImage());
        expert.setRating(response.rating());
        expert.setUserStatus(response.userStatus());
        expert.setBalance(response.balance());
        return expert;
    }*/

    /*public static Expert fromChangePasswordRequest(ExpertChangePasswordRequest request) {
        if (request == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setId(request.id());
        expert.setPassword(request.newPassword());
        return expert;
    }*/
}
