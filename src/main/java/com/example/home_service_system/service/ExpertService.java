package com.example.home_service_system.service;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface ExpertService {
    ExpertResponse save(@Valid ExpertSaveRequest expertSaveRequest);

    ExpertResponse update(@Valid ExpertUpdateRequest expertUpdateRequest);

    ExpertResponse findByIdAndIsDeletedFalse(Long id);

    List<ExpertResponse> findAll();

    ExpertResponse findByUsername(String username);

    void deleteById(Long id);

    void changePassword(Long id, @Valid ExpertChangePasswordRequest request);
}
