package com.example.home_service_system.service;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.SubService;

import java.util.List;

public interface SubServiceService {
    SubServiceResponse save(SubServiceSaveRequest request);

    SubServiceResponse update(SubServiceUpdateRequest request);

    SubServiceResponse findById(Long id);

    SubService findSubServiceById(Long id);

    List<SubServiceResponse> findAllByIsDeletedFalse();

    List<SubService>  findAllSubServicesByIsDeletedFalse();

    List<SubServiceResponse> findAllByMainServiceId(Long mainServiceId);

    void softDeleteById(Long id);

    void softDeleteAllByMainServiceId(Long mainServiceId);

    void addExpertToSubService(Long subServiceId, Long expertId);

    void removeExpertFromSubService(Long subServiceId, Long expertId);

    SubServiceResponse updateSubService(Long subServiceId, SubServiceUpdateRequest updateRequest);


}
