package com.example.home_service_system.mapper.customMappers;

import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.SubService;

import java.util.stream.Collectors;

public class CustomExpertMapper {
    public static ExpertResponse to(Expert expert) {
        return new ExpertResponse(
                expert.getId(),
                expert.getFirstName(),
                expert.getLastName(),
                expert.getUsername(),
                expert.getNationalId(),
                expert.getPhoneNumber(),
                expert.getBirthday(),
                expert.getEmail(),
                expert.getCreatedAt(),
                expert.getUpdatedAt(),
                expert.getExpertImage(),
                expert.getRating(),
                expert.getUserStatus(),
                expert.getBalance(),
                expert.getOrderList().stream().map(order -> order.getId())
                        .collect(Collectors.toList()),
                expert.getExpertServiceFields().stream().map(SubService::getId)
                        .collect(Collectors.toList())
               /* expert.getCustomerCommentAndRateList().stream().map(comment -> comment.getId())
                        .collect(Collectors.toList())*/
        );
    }

    public static Expert fromSaveRequest(ExpertSaveRequest request) {
        Expert expert = new Expert();
        expert.setFirstName(request.firstName());
        expert.setLastName(request.lastName());
        expert.setUsername(request.username());
        expert.setPassword(request.password());
        expert.setNationalId(request.nationalId());
        expert.setPhoneNumber(request.phoneNumber());
        expert.setBirthday(request.birthday());
        expert.setEmail(request.email());
        expert.setExpertImage(request.expertImage());
        expert.setRating(0);
        expert.setBalance(0L);
        return expert;
    }

    public static Expert fromUpdateRequest(ExpertUpdateRequest request) {
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
    }

    public static Expert toExpertFromResponse(ExpertResponse response) {
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
    }

    public static ExpertUpdateRequest toUpdateRequest(Expert expert) {
        return new ExpertUpdateRequest(
                expert.getId(),
                expert.getFirstName(),
                expert.getLastName(),
                expert.getUsername(),
                expert.getPassword(),
                expert.getNationalId(),
                expert.getPhoneNumber(),
                expert.getBirthday(),
                expert.getEmail(),
                expert.getExpertImage(),
                expert.getRating(),
                expert.getUserStatus(),
                expert.getBalance(),
                expert.getOrderList().stream().map(order -> order.getId())
                        .collect(Collectors.toList()),
                expert.getExpertServiceFields().stream().map(SubService::getId)
                        .collect(Collectors.toList())
                /*expert.getCustomerCommentAndRateList().stream().map(comment -> comment.getId())
                        .collect(Collectors.toList())*/
        );
    }
}
