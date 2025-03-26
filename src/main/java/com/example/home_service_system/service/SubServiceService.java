package com.example.home_service_system.service;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.SubService;

import java.util.List;

public interface SubServiceService {
    SubServiceResponse save(SubServiceSaveRequest request);

    SubServiceResponse update(SubServiceUpdateRequest request);

    SubServiceResponse findByIdAndIsDeletedFalse(Long id);

    SubService findSubServiceByIdAndIsDeletedFalse(Long id);

    List<SubServiceResponse> findAllAndIsDeletedFalse();

    List<SubService> findAllSubServicesAndIsDeletedFalse();

    List<SubServiceResponse> findAllByMainServiceId(Long mainServiceId);

    List<SubServiceResponse> findAllByExpertIdAndIsDeletedFalse(Long expertId);

    void softDeleteSubServiceAndExpertFieldsAndOrdersAndCommentAndRateAndSuggestionsById(Long id);

    void softDeleteById(Long id);

    void addExpertToSubService(Long subServiceId, Long expertId);

    void removeExpertFromSubService(Long subServiceId, Long expertId);


}
