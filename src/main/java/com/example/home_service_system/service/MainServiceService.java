package com.example.home_service_system.service;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;

import java.util.List;

public interface MainServiceService {
    MainServiceResponse save(MainServiceSaveRequest request);

    MainServiceResponse update(MainServiceUpdateRequest request);

    void softDeleteMainServiceAndSubServicesAndOrdersAndSuggestionsAndCommentAndRate(Long id);

    void softDelete(Long id);

    MainServiceResponse findByIdAndIsDeletedFalse(Long id);

    MainService findMainServiceByIdAndIsDeletedFalse(Long id);

    List<MainServiceResponse> findAllAndIsDeletedFalse();
}
