package com.example.home_service_system.service;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    AdminResponse save(@Valid AdminSaveRequest adminSaveRequest);

    AdminResponse update(@Valid AdminUpdateRequest adminUpdateRequest);

    AdminResponse findByIdAndIsDeletedFalse(Long id);

    List<AdminResponse> findAll();

    AdminResponse findByUsername(String username);

    void deleteById(Long id);

    void changePassword(Long id, @Valid AdminChangePasswordRequest request);
}
