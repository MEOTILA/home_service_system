package com.example.home_service_system.mapper.customMappers;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;

import java.util.stream.Collectors;

public class CustomMainServiceMapper {
    public static MainServiceResponse to(MainService mainService) {
        return new MainServiceResponse(
                mainService.getId(),
                mainService.getName(),
                mainService.getSubServices().stream()
                        .map(subService -> subService.getId())
                        .collect(Collectors.toList())
        );
    }

    public static MainService fromSaveRequest(MainServiceSaveRequest request) {
        MainService mainService = new MainService();
        mainService.setName(request.name());
        return mainService;
    }

    public static MainService fromUpdateRequest(MainServiceUpdateRequest request) {
        MainService mainService = new MainService();
        mainService.setId(request.id());
        mainService.setName(request.name());
        return mainService;
    }

    public static MainService toMainServiceFromResponse(MainServiceResponse response) {
        MainService mainService = new MainService();
        mainService.setId(response.id());
        mainService.setName(response.name());
        return mainService;
    }
}

