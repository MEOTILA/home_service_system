package com.example.home_service_system.mapper.customMappers;

import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.SubService;

import java.util.List;
import java.util.stream.Collectors;

public class CustomExpertMapper {
    public static ExpertResponse toExpertResponse(Expert expert) {
        if (expert == null) {
            return null;
        }
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
                mapToIds(expert.getOrderList()),
                mapToIds(expert.getExpertServiceFields()),
                mapToIds(expert.getCustomerCommentAndRateList())
        );
    }

    public static Expert fromSaveRequest(ExpertSaveRequest request) {
        if (request == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setFirstName(request.firstName());
        expert.setLastName(request.lastName());
        expert.setUsername(request.username());
        expert.setNationalId(request.nationalId());
        expert.setPhoneNumber(request.phoneNumber());
        expert.setBirthday(request.birthday());
        expert.setEmail(request.email());
        expert.setExpertImage(request.expertImage());
        return expert;
    }

    public static Expert fromUpdateRequest(ExpertUpdateRequest request) {
        if (request == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setId(request.id());
        expert.setExpertImage(request.expertImage());
        expert.setRating(request.rating());
        expert.setUserStatus(request.userStatus());
        expert.setBalance(request.balance());
        return expert;
    }

    private static <T> List<Long> mapToIds(List<T> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .filter(item -> item instanceof Order || item instanceof SubService
                        || item instanceof CustomerCommentAndRate)
                .map(item -> {
                    if (item instanceof Order) {
                        return ((Order) item).getId();
                    } else if (item instanceof SubService) {
                        return ((SubService) item).getId();
                    } else if (item instanceof CustomerCommentAndRate) {
                        return ((CustomerCommentAndRate) item).getId();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}

