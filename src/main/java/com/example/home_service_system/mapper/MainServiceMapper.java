package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;

import java.util.ArrayList;

public class MainServiceMapper {
    public static MainServiceResponse to(MainService mainService) {
        if (mainService == null)
            return null;
        return new MainServiceResponse(
                mainService.getId(),
                mainService.getName(),
                /*mainService.getSubServices().stream()
                        .map(subService -> subService.getId())
                        .collect(Collectors.toList())*/
                mainService.getSubServices() != null ? mainService.getSubServices().stream()
                        .map(s -> s.getId()).toList()
                        : new ArrayList<>()
        );
    }

    public static MainService fromSaveRequest(MainServiceSaveRequest request) {
        if (request == null)
            return null;
        MainService mainService = new MainService();
        mainService.setName(request.name());
        return mainService;
    }

    public static MainService fromUpdateRequest(MainServiceUpdateRequest request) {
        if (request == null)
            return null;

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

