package com.example.home_service_system.service;

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

    List<AdminResponse> findAll();

    Optional<Admin> findById(Long id);

    void deleteById(Long id);

    Optional<Admin> findByUsername(String username);
}
