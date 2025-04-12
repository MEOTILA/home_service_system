package com.example.home_service_system.service;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.util.List;

public interface AdminService {
    AdminResponse save(@Valid AdminSaveRequest adminSaveRequest) throws MessagingException;

    AdminResponse update(@Valid AdminUpdateRequest adminUpdateRequest);

    AdminResponse findByIdAndIsDeletedFalse(Long id);

    Admin findAdminByIdAndIsDeletedFalse(Long id);

    List<AdminResponse> findAllAndIsDeletedFalse();

    AdminResponse findByUsernameAndIsDeletedFalse(String username);

    void changePassword(@Valid AdminChangePasswordRequest request);

    void softDeleteById(Long id);
}
