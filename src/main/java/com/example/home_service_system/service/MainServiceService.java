package com.example.home_service_system.service;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;

import java.util.List;

public interface MainServiceService {
    MainServiceResponse save(MainServiceSaveRequest request);

    MainServiceResponse update(MainServiceUpdateRequest request);

    void softDelete(Long id);

    MainServiceResponse findById(Long id);

    List<MainServiceResponse> findAll();
}
