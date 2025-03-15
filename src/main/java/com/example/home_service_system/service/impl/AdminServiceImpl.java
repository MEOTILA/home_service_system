package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.AdminMapper;
import com.example.home_service_system.repository.AdminRepository;
import com.example.home_service_system.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Override
    public AdminResponse save(@Valid AdminSaveRequest adminSaveRequest) {
        Optional<Admin> optionalAdmin =
                adminRepository.findByUsernameAndPhoneNumberAndNationalIDAndEmail(
                        adminSaveRequest.username(),
                        adminSaveRequest.phoneNumber(),
                        adminSaveRequest.nationalID(),
                        adminSaveRequest.email());

        if (optionalAdmin.isPresent()) {
            throw new CustomApiException("Admin with username {"
                    + adminSaveRequest.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIABLE_ENTITY);
        }

        Admin admin = adminRepository.save(adminMapper.fromSaveRequest(adminSaveRequest));
        log.info("Admin with id {} saved", admin.getId());
        return adminMapper.to(admin);
    }

    @Override
    public AdminResponse update(AdminUpdateRequest adminUpdateRequest) {
        return null;
    }

    @Override
    public List<AdminResponse> findAll() {
        return List.of();
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return Optional.empty();
    }
}
