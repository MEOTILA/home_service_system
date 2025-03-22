package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.SubService;

import java.util.stream.Collectors;

public class SubServiceMapper {
    public static SubServiceResponse to(SubService subService) {
        if (subService == null) {
            return null;
        }
        return new SubServiceResponse(
                subService.getId(),
                subService.getName(),
                subService.getBaseCost(),
                subService.getDescription(),
                subService.getMainService() != null ? subService.getMainService().getId() : null,
                subService.getCreatedAt(),
                subService.getUpdatedAt(),
                subService.getOrderList() != null ? subService.getOrderList().stream()
                        .map(order -> order.getId()).collect(Collectors.toList()) : null,
                subService.getExpertList() != null ? subService.getExpertList().stream()
                        .map(expert -> expert.getId()).collect(Collectors.toList()) : null
        );
    }

    public static SubService fromSaveRequest(SubServiceSaveRequest request) {
        if (request == null) {
            return null;
        }
        SubService subService = new SubService();
        subService.setName(request.name());
        subService.setBaseCost(request.baseCost());
        subService.setDescription(request.description());
        //subService.setMainService(request.mainService());
        return subService;
    }

    public static SubService fromUpdateRequest(SubServiceUpdateRequest request) {
        if (request == null) {
            return null;
        }
        SubService subService = new SubService();
        subService.setId(request.id());
        subService.setName(request.name());
        subService.setBaseCost(request.baseCost());
        subService.setDescription(request.description());
        //subService.setMainService(request.mainService());
        return subService;
    }
}
