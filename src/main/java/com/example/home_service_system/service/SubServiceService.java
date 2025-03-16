package com.example.home_service_system.service;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;

import java.util.List;

public interface SubServiceService {
    SubServiceResponse save(SubServiceSaveRequest request);

    SubServiceResponse update(SubServiceUpdateRequest request);

    SubServiceResponse findById(Long id);

    List<SubServiceResponse> findAll();

    List<SubServiceResponse> findAllByMainServiceId(Long mainServiceId);

    void softDeleteById(Long id);

    void addExpertToSubService(Long subServiceId, Long expertId);

    void removeExpertFromSubService(Long subServiceId, Long expertId);

    SubServiceResponse updateSubService(Long subServiceId, SubServiceUpdateRequest updateRequest);


}
